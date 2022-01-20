package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Member;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepoTest {

    ItemRepo itemRepo = new ItemRepo();

    @AfterEach
    void afterEach(){
        itemRepo.clearStore();
    }

    @Test
    void save() {

        //given
        Item item = new Item("aa",10000,10);
        //when
        Item saveItem = itemRepo.save(item);
        //then
        Item findItem = itemRepo.findById(item.getId());
        org.assertj.core.api.Assertions.assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {

        //given
        Item item = new Item("item1",10000,10);
        Item item2 = new Item("item2",20000,20);
        itemRepo.save(item);
        itemRepo.save(item2);

        //when

        List<Item> list = itemRepo.findAll();
        //then
        org.assertj.core.api.Assertions.assertThat(list.size()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(list).contains(item,item2);
    }

    @Test
    void update() {
        //given
        Item item = new Item("item1",10000,10);
        itemRepo.save(item);
        Long id = item.getId();
        //when
        Item item2 = new Item("item2",20000,20);
        itemRepo.update(id, item2);
        //then
        Item after = itemRepo.findById(id);
        org.assertj.core.api.Assertions.assertThat(after).isEqualTo(item);
    }

}