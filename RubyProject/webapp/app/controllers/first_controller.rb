class FirstController < ApplicationController
	def index
		@sessionLogin = session[:loggedIn]
		@sessionId = session[:userId]
	end
	
	
	 
	
	#def login		
		#@email = params[:email]
		#@password = params[:password]
		#@user = User.where("email = ? AND password = ?", @email, @password)
		
		#if @user.size > 0 
		#	@something = @user.first.id
		#	@firstName = @user.first.first_name
		#	session[:userId] = @something
		#	session[:loggedIn] = true
		#	
		#	@sessionLogin = session[:loggedIn]
		#	@sessionId = session[:userId]	
		#else 		
		#	redirect_to(:action => 'index')
		#end		
	#end
	
	def login
	  user = User.find_by_email(params[:email])
	  if user && user.authenticate(params[:password])
	  
		session[:userId] = user.id
		session[:loggedIn] = true
		@user = user
		#redirect_to root_url, :notice => "Logged in!"
	  else
		@loginResult = false
		flash.now.alert = "Invalid email or password"
		render "index"
	  end
	end
	
	
	def logout
		#session id deleted here
		session[:userId] = nil
		session[:loggedIn] = false
		redirect_to(:action => 'index')
		
	end
	
	
	
end
