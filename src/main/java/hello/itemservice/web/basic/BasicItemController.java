package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final 을 가진 변수의 생성자 자동생성
public class BasicItemController {
    private final ItemRepo itemRepo;

    @GetMapping
    public String items(Model model){
        List<Item> all = itemRepo.findAll();
        model.addAttribute("items",all);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepo.findById(itemId);
        model.addAttribute(item);
        return "basic/item";

    }

    @GetMapping("/add")
    public String addForm(){
        return"basic/addForm";

    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, int price, int quantity, Model model){
        Item item = new Item(itemName,price,quantity);
        itemRepo.save(item);

        model.addAttribute("item",item);
        return"basic/item";

    }
  //  @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){

        itemRepo.save(item);
        model.addAttribute("item",item); // ModelAttribute() 이 이름을 사용하기 때문에 생략가능
                                                    // (이름 설정안할시 첫 글자만 소문자로 바꿔서저장)-
                                                    // 모델에 자동추가
       return"basic/item";

    }
    //post로 요청을 하고 post로 응답이 들어감 -> 새로고침시 마지막 행동을 또함 add+ ->리다이랙트로 바꿈
  //  @PostMapping("/add")
    public String addItemV3(Item item){ // 최종 model에 자동저장까지 ~~
        itemRepo.save(item);
        return"basic/item";

    }

   // @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepo.save(item);
        return"redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV5(Item item, RedirectAttributes redirectAttributes){
        Item itemId = itemRepo.save(item);
        redirectAttributes.addAttribute("itemId",itemId.getId());
        redirectAttributes.addAttribute("status",true);
        return"redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepo.findById(itemId);
        model.addAttribute(item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepo.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    //test
    @PostConstruct
    public void init(){
        itemRepo.save(new Item("jiwoo",1000,100));
        itemRepo.save(new Item("seokjun",2000,200));
    }

}
