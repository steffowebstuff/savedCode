class ProjectsController < ApplicationController
	#before_filter :check_id, :only => :edit
	before_filter :check_id, :only => [:edit, :delete]
	
	def index
		if session[:loggedIn] == true						
			@projects = Project.all
		else 
			flash[:notice] = "You are not logged in"
		end
	end

	def show 
		if session[:loggedIn] == true	
			@validUser = false
			@userId = session[:userId]
			@project = Project.find(params[:id])
			#@projectUserId = @project.owner_id
			@prownerId = @project.owner_id
			@prowner = User.find(@prownerId)

			if @prownerId == @userId
				@validUser = true
			end

			@prusers = @project.users

			@prusers.each do |pruser|	
			@validPruser = pruser
				if pruser.id == @userId
					@validUser = true
				end
			end 



			@pMinigoals = @project.minigoals
			@projectName = @project.name
			@projectDescription = @project.description



		else 
			flash[:notice] = "You are not logged in"
		end
	end

	def create

		#flash[:notice] = params

		@project = Project.new(params[:project])
		@userId = session[:userId]
		@user = User.find(@userId)
		@project.owner_id = @userId

		User.all.each do |u|
			flash[:notice] = params[u.id]
			if  params[u.id.to_s]
				@pruser = User.find(u.id)
				@project.users << @pruser

			end
		end

		@project.users << @user	
		if @project.save
			redirect_to projects_path
		else 
			render :action => "new"
		end
	end

	def destroy
	#before-filter should be here
		#before_filter :check_id
		@sessionId = session[:userId]
		@project = Project.find(params[:id])
		@projectUserId = @project.owner_id
		if @projectUserId == @sessionId 
			@crap = Project.find(params[:id])
			@crap.destroy
			redirect_to projects_url
		end
	end

	def new
		if session[:loggedIn] == true
		@users = User.all
		@nrusers = @users.size
		@userSize = @users.size
		@userId = session[:userId]
			@project = Project.new
		else 
				flash[:notice] = "You are not logged in"
		end
	end

	def edit
		if session[:loggedIn] == true	
			#flash[:notice] = "hejsan"
			@sessionId = session[:userId]
			@validUser = false
			@project = Project.find(params[:id])
			@sessionId = session[:userId]
			@projectUserId = @project.owner_id
			@prowner = User.find(@project.owner_id)				

			if @prowner.id == @sessionId
				@validUser = true
			end
			@prusers = @project.users
			#@validUser = false
			@prusers.each do |pruser|	
			@checkedUser = pruser
			
				if pruser.id == @sessionId
					
					@validUser = true
				end
			end 
			
			@checkedUsers = @project.users
			@checkedUsers.all.each do |checkedUser|
				@ckUser = checkedUser
			end 
			


		else 
			flash[:notice] = "You are not logged in"
		end
	end	

	def update

		@userId = session[:userId]
		@project = Project.find(params[:id])
		@user = User.find(@userId)

		User.all.each do |u|

				@pruser = User.find(u.id)
				@project.users.delete(@pruser)
				#flash[:notice] = params[u.id]
				if  params[u.id.to_s]
					#flash[:notice] = "testing"
					#@project.users.delete(@pruser)
					@project.users << @pruser

				end
			end
		@project.users << @user	
		if @project.update_attributes(params[:project])
			redirect_to projects_path
		else
			render :action => "edit"
		end
	end

	def userchange
		#lite kod

		@project = Project.find(params[:id])

		params[:User]

		if @project.update_attributes(params[:project])
			redirect_to projects_path
		else
			render :action => "edit"
		end
	end
	
	def check_id
		@userId = session[:userId]
		@project = Project.find(params[:id])
		@projectOwner= @project.owner_id
		
		if @userId != @projectOwner
			redirect_to project_path
		end
	end
end