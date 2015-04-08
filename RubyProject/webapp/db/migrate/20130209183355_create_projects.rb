class CreateProjects < ActiveRecord::Migration
  def change
    create_table :projects do |t|
		
	  t.string "name", :limit => 20	
	  t.text :description 
	  t.string :name
	  t.date :start
	  t.date :end
	  t.integer :owner_id
	  
      t.timestamps
    end
  end
end
