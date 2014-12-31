package com.panopset.demo.dao;

import java.util.List;

import com.panopset.demo.bean.Store;

public interface StoreDAO {

	void insertStore(Store store);

	Store getStoreById(int storeId);

	Store getStore(String storeName);

	List<Store> getAllStores();

}
