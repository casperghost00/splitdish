package com.splitdish.pos.menu;

import com.splitdish.lib.MenuItemList;

public class GlobalMenu {

	private static MenuItemList mGlobalMenu = null;
	
	private GlobalMenu() {	}
	
	// Returns the single instance of MenuItemList when successfully called.
	public static MenuItemList getGlobalMenu() {		
		if(mGlobalMenu == null) {
			mGlobalMenu = new MenuItemList();
		}
		return mGlobalMenu;
	}
	
	public static void setMenuList(MenuItemList menuList) {
		mGlobalMenu =  (MenuItemList)menuList.clone();
	}
}
