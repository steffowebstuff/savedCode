class MinigoalsController < ApplicationController

	before_filter :check_id, :only => [:edit, :delete]

	def index
		if session[:loggedIn] == true	
			@minigoals = Minigoal.all		
		else 
			flash[:notice] = "You are not logged in"
		end
	end

	def show
		if session[:loggedIn] == true
			@sessionUserId = session[:userId]		
			@currentUser = User.find(@sessionUserId)
			@currentUserId = @currentUser.id
			@minigoal = Minigoal.find(params[:id])
			@minigoalProject = @minigoal.project_id
			@projectName = Project.find_by_id(@minigoalProject)
			@minigoalStatus = @minigoal.status_id 
			@status = Status.find_by_id(@minigoalStatus)
			@mgUserId = @minigoal.user_id
			@mgUser =  User.find_by_id(@mgUserId)

			else 
			flash[:notice] = "You are not logged in"
			end
	end

	def new
		if  session[:loggedIn] == true			
			@project = Project.find(params[:project_id])
			@sessionId = session[:userId]
			@prusers = @project.users
			@validUser = false
			@prusers.each do |pruser|		
				if pruser.id == @sessionId
					@validUser = true
				end
			end 			
				@minigoal = Minigoal.new
		else 
			flash[:notice] = "You are not logged in"
		end
	end

	def create

		@minigoal = Minigoal.new(params[:minigoal])	
		@project = Project.find(params[:project_id])	
		@minigoal.project = @project
		@minigoal.user_id = session[:userId] 
		if @minigoal.save
			redirect_to projects_path			
		else
			render :action => "new"
			@validUser = true
		end

	end

	def destroy
		@sessionId = session[:userId]				
		@minigoal = Minigoal.find(params[:id])
		@minigoalUserId = @minigoal.user_id
		if @sessionId == @minigoalUserId
			@minigoal.destroy
		end
		redirect_to projects_path
	end

	def edit
		if session[:loggedIn] == true	
		@validUser = false
			@sessionId = session[:userId]			
			@minigoal = Minigoal.find(params[:id])	
			@minigoalUserId = @minigoal.user_id
			if @minigoalUserId == @sessionId
				#flash[:notice] = "You do not have authority to edit this minigoal"
				@validUser = true
			end
		else 
			flash[:notice] = "You are not logged in"
		end
	end

	def update
		@minigoal = Minigoal.find(params[:id])	
		if @minigoal.update_attributes(params[:minigoal])
			redirect_to projects_path
		else
			render :action => "edit"
		end
	end
	
	def check_id
		@userId = session[:userId]
		@minigoal = Minigoal.find(params[:id])
		@minigoalUserId = @minigoal.user_id
		
		if @userId != @minigoalUserId
			redirect_to projects_path
		end
	end

end