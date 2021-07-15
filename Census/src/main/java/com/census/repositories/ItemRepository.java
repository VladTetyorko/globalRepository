package com.census.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.census.entities.Category;
import com.census.entities.Item;
import com.census.entities.Location;
import com.census.entities.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	public List<Item> findByTranslations_NameIsContaining(String name);

	public List<Item> findByOwner(User user);

	public List<Item> findByLocation(Location location);

	public List<Item> findByCategory(Category category);

	public List<Item> findByLocationAndTranslations_NameIsContaining(Location location, String partOfName);

	public List<Item> findByCategoryAndTranslations_NameIsContaining(Category category, String partOfName);

	public List<Item> findByOwnerAndTranslations_NameIsContaining(User principal, String partOfName);

	@Query(value = "select I.id as id,I.name as name ,IL.location_id as location_id, IU.user_id as user_id, IC.category_id as category_id \n"
			+ "from items I \n" + "left outer join item_location IL on I.id=IL.item_id \n"
			+ "left outer join item_user IU on I.id=IU.item_id \n"
			+ "left outer join item_category IC on I.id=IC.item_id \n"
			+ "left outer join i18n_items T on I.id=T.item_id \n"
			+ "where T.TRANSLATION_NAME||T.TRANSLATION_DESCRIPTION LIKE %?1% ", nativeQuery = true)
	public List<Item> findByNameDescription(String name);

}
