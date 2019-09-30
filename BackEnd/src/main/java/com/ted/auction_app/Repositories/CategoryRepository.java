package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Category.CategoryEntity;
import com.ted.auction_app.Models.Category.Enum.CategoryEnumDTO;
import com.ted.auction_app.Models.Item.ItemEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findAll();
    CategoryEntity findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM item_category WHERE item_id=?1 AND category_id=?2", nativeQuery = true)
    void deleteCategoryItemJoin(Integer categoryId, Integer itemId);

    @Query(value = "select * from categories inner join item_category on categories.id=item_category.category_id where item_category.item_id = ?1", nativeQuery = true)
    List<CategoryEntity> findByItemId(Integer itemId);

    @Query("SELECT u.items FROM CategoryEntity u WHERE u.id = ?1")
    List<ItemEntity> getItemsByCategoryId(Integer categoryId);

    @Query(value = "select new com.ted.auction_app.Models.Category.Enum.CategoryEnumDTO(ic.id as 'id', ic.name as 'name', count(ic.item.id) as 'itemCount') from CategoryEntity ic group by ic.id order by num desc", nativeQuery = true)
    List<CategoryEnumDTO> categoryItemGroup();
}



//id, name, count(ic.item_id) as itemCount from item_category ic inner join categories c on ic.category_id=c.id group by category_id order by num desc