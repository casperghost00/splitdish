package com.splitdish.pos;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.splitdish.lib.MenuItemList;
import com.splitdish.lib.Utilities;
import com.splitdish.pos.R;
import com.splitdish.pos.menu.GlobalMenu;

import android.app.Application;


public class SDApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		GlobalMenu.setMenuList(getMenuItemList());
	}
	
	
	protected MenuItemList getMenuItemList() {
		MenuItemList menuItemList = null;
		String jsonMenu = null;
		try {
			jsonMenu = Utilities.getTextFromRawResource(this, R.raw.sample_menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			JSONObject jMenu = new JSONObject(jsonMenu);
			menuItemList = new MenuItemList(jMenu);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return menuItemList;
	}
}
