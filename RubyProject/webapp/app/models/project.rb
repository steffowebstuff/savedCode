class Project < ActiveRecord::Base
	has_many :minigoals
	has_and_belongs_to_many :users
	
	validates :name, 
			  :presence => {:message => ": Field seems to be missing"},
			  :length => {:minimum => 5, :message => ": Must be longer than 4 characters"}
	validates :name, :uniqueness => true
	
	validates :description, 
			  :presence => {:message => ": Field seems to be missing"},
			  :length => {:maximum => 250, :message => ": Field can not be longer than 250 characters"}
			  
	validates :start, 
			  :presence => {:message => ": You must fill in the start date right"}
			  
	validates :end, 
			  :presence => {:message => ": You must fill in the end date right"}
			 
			  
end
