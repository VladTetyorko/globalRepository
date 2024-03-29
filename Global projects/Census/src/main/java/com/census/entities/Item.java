package com.census.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

import org.springframework.context.i18n.LocaleContextHolder;

@Entity
@Table(name = "ITEMS")
@SecondaryTables({
		@SecondaryTable(name = "ITEM_USER", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")),
		@SecondaryTable(name = "ITEM_CATEGORY", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")),
		@SecondaryTable(name = "ITEM_PICTURE", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")),
		@SecondaryTable(name = "ITEM_LOCATION", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ITEM_ID", referencedColumnName = "ID")) })
public class Item extends TableEntity {

	public Item() {
	}

	public Item(int id, Category category, Location location, User owner) {
		super();
		this.id = id;
		this.category = category;
		this.location = location;
		this.owner = owner;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(table = "ITEM_CATEGORY", name = "CATEGORY_ID")
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(table = "ITEM_LOCATION", name = "LOCATION_ID")
	private Location location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(table = "ITEM_USER", name = "USER_ID")
	private User owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
	private List<ItemTranslation> translations;

	@Column(table = "ITEM_PICTURE", name = "PICTURE")
	private String picture;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<ItemTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<ItemTranslation> translations) {
		this.translations = translations;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public HashMap<String, ItemTranslation> getTranslationList() {
		List<ItemTranslation> itemTranslations = getTranslations();
		Map<String, ItemTranslation> map = itemTranslations.stream()
				.collect(Collectors.toMap(ItemTranslation::getLanguage, Function.identity()));
		return (HashMap<String, ItemTranslation>) map;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + "category=" + category.getName() + ", location=" + location.getName() + ", owner="
				+ owner.getUsername() + "]";
	}

	@Override
	public String[] toTableRow() {
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		Map<String, ItemTranslation> map = this.getTranslationList();
		Integer id = getId();
		String row[] = { id.toString(), map.get(language).getName(), map.get(language).getDescription(),
				this.getCategory().getName(), this.getLocation().getName(), this.getOwner().getUsername() };
		return row;
	}

	@Override
	public String[] getTableHeader() {
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		switch (language) {
		case "ru": {
			String row[] = { "ID", "Имя", "Описание", "Категория", "Локация", "Владелец" };
			return row;
		}
		case "ua": {
			String row[] = { "ID", "Ім'я", "Описання", "Категорія", "Локація", "Власник" };
			return row;
		}
		default: {
			String row[] = { "ID", "NAME", "Description", "Category", "Location", "Owner" };
			return row;
		}
		}
	}

}
