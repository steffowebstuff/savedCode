class UsersController < ApplicationController
	before_filter :check_id, :only => [:edit, :delete]
	
	def index
		if session[:loggedIn] == true
			@users = User.all 
			
		else 
			flash[:notice] = "You are not logged in"
		end
	end
	
	def new		
		
			@user = User.new	
		
	end
	
	def show 
	    if session[:loggedIn] == true			
			user = User.where("id = ?", params[:id])
			@sessionId = session[:userId] 
			@userId = user.first.id	
			if !user.empty?
				@userfname = user.first.first_name
				@userlname = user.first.last_name
				@email = user.first.email
				@uprojects = user.first.projects
			else
				flash[:notice] = "This user does not exist"
			end
		else 
			flash[:notice] = "You are not logged in"
		end
		
	end
	
	
	def search
		if session[:loggedIn] == true
			@searchPhrase =  params[:search]
			@users= User.find(:all, :conditions=> ["first_name like :eq", {:eq => "%" + @searchPhrase + "%"}])		
			@size = @users.size
			
			if @size > 0
				@first = @users.first.first_name
			end
			
		else 
			flash[:notice] = "You are not logged in"
		end			
	end
	
	def create	    
		@user = User.new(params[:user]) 
		if @user.save
			redirect_to root_url
		else
			render :action => "new"
		end
		
	end
	
	def update
		@user = User.find(params[:id])	
		if @user.update_attributes(params[:user])
			redirect_to users_path
		else
			render :action => "edit"
		end
	end
	
	def edit
		if session[:loggedIn] == true	
			@sessionId = session[:userId] 
			@user = User.find(params[:id])
			@userId = @user.id	
		else 
			flash[:notice] = "You are not logged in"
		end	
	end
	
	def destroy
		@sessionId = session[:userId] 
		@user = User.find(params[:id])
		@userId = @user.id
		if (@userId == @sessionId)
			@user.destroy
		end
		redirect_to first_logout_path
	end
	
	def check_id
		@sessionUserId = session[:userId]
		@user = User.find(params[:id])
		@userId = @user.id
		
		if @userId != @sessionUserId
			redirect_to user_path
		end
	end
	
end

