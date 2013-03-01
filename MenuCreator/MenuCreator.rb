require 'rubygems'
require 'json'

class MenuCreator
 
  def initialize(old_menu_path = nil)
    @item_array = Array.new
    unless(old_menu_path.nil?)
      old_menu_json = ""
      File.open(old_menu_path, 'r').each_line do |line|
        old_menu_json << line
      end
      parse_old_menu_json(old_menu_json)
    end  
  end
  
  def parse_old_menu_json(old_menu_json)
    menu = JSON.parse(old_menu_json)
    menu.each do |item|
      @item_array << MenuItem.new(item)
    end
  end
  
  def item_array
    @item_array
  end
  
  def start_menu_creation(file_destination)
    continue = true
    while(continue)
      menu_item = MenuItem.new()
      unless(@item_array.empty?)
        menu_item.id = @item_array.last().id + 1
      else
        menu_item.id = 1
      end
      
      get_item_name(menu_item)
      get_item_description(menu_item)
      get_item_category(menu_item)
      get_item_price(menu_item)
      @item_array << menu_item
      continue = add_more_items?
    end
    File.open(file_destination, 'w') { |file| file.write(JSON.pretty_generate(@item_array)) }
  end
  def get_item_name(menu_item)
    print "Item Name: "
    menu_item.name = gets.chomp
  end
  
  def get_item_description(menu_item)
    print "Describe the item: "
    menu_item.description = gets.chomp
  end
  
  def get_item_category(menu_item)
    print "Category: "
    menu_item.category = gets.chomp
  end
  
  def get_item_price(menu_item)
    print "Price (in cents): "
    menu_item.price = gets.to_i
  end
  
  def add_more_items?
    print "Add another item? (y/n)"
    more = gets.chomp
    if(more.eql? "y")
      return true
    elsif(more.eql? "n")
      return false
    end
    add_more_items?
  end
  
end

class MenuItem
  attr_accessor :id, :name, :description, :category, :price
  
  def initialize(init_params ={})
    unless(init_params.empty?)
      @id = init_params["id"]
      @name = init_params["name"]
      @description = init_params["description"]
      @category = init_params["category"]
      @price = init_params["price"]
    end
  end
  
  def to_json
    {:id => @id, :name => @name,:description => @description,
     :category => @category, :price => price }.to_json
  end
  
  def to_json(*a)
    {:id => @id, :name => @name,:description => @description,
    :category => @category, :price => price }.to_json(*a)
  end
end

creator = MenuCreator.new("MenuOutput/sample.json")
#creator = MenuCreator.new()
creator.start_menu_creation("MenuOutput/sample.json")
