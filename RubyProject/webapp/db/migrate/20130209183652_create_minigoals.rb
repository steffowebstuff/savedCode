class CreateMinigoals < ActiveRecord::Migration
  def change
    create_table :minigoals do |t|
	  
	  t.references :project
	  t.references :status
	  t.references :user
	  t.string :name
	  t.date :start
	  t.date :end  #mg2.DateTime.new(2013,01,02)... osv
      t.timestamps
    end
  end
end