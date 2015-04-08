class User < ActiveRecord::Base
	
	
	has_secure_password
	
	
	has_and_belongs_to_many :projects
	has_many :minigoals
	
	validates :first_name, 
			  :presence => {:message => "First name seems to be missing"},
			  :length => {:maximum => 20, :message => "First name of the user can not be longer than 20 characters"}
			  			  
	validates :last_name, 
			  :presence => {:message => "Last name seems to be missing"},
			  :length => {:maximum => 40, :message => "Last name of the user can not be longer than 20 characters"}
			  
	validates :email, 
			  :presence => {:message => "Email seems to be missing"}
			  
	validates :email, :uniqueness => true		  
			  
	validates :email,  :format => { :with => /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})\Z/, :message =>"Invalid email"}
	
	validates :password, 
			  :presence => {:message => "Password seems to be missing"},
			  :length => {:minimum => 5, :maximum => 30, :message => "Password must be at least 5 and maximum 30 characters"}
			  #Get a maximum length for password to!
			  
	
end
