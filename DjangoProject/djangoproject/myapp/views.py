# Create your views here.
from django.contrib.auth import authenticate, login, logout
from django.shortcuts import get_list_or_404, render, redirect, get_object_or_404
from myapp.models import Project, ProjectForm, Minigoal, LoginForm, MinigoalForm, forms, SearchForm, User, UserForm
import datetime
from django.contrib.auth.decorators import login_required, permission_required
from django.http import HttpResponse

#basic methods
def home(request):
	logout(request)
	return redirect('project_list')

def error_permission(request):
	return HttpResponse("Authority denied")

def search(request):
	if 'q' in request.GET:
		message = 'You searched for: %r' % request.GET['q']
		q = request.GET['q']
		users = User.objects.filter(username__icontains=q)
		if users.count == 0:
			emptyMessage = "Your search did not get any results"
		
		return render(request, 'users/search.html', {"users" : users, "message" : message})
	else:		
		message = 'You submitted an empty form.'
	return HttpResponse(message)


	
def login_user(request):
	message = ''
	if request.method == "POST":	
		form = LoginForm(request.POST)
		if form.is_valid():
			username_to_try = form.cleaned_data["username"]
			password_to_try = form.cleaned_data["password"]			
			user = authenticate(username=username_to_try, password=password_to_try)			
			if user is not None:
				if user.is_active:
					login(request, user)
					request.session['has_logged_in'] = True
					return redirect('project_list')
				else:
					return HttpRespeonse("<h1>Trouble here</h1>")
			else:
				message = "Wrong username and/or password"
	else:
		form = LoginForm()
	#if request.session['has_logged_in'] == True:
	#	loginmessage = 'logged in'
	#else:
	#	loginmessage = 'logged out'
	return render(request, 'projects/login.html' ,{'form' : form, 'message' : message})
	
def logout_user(request):
	logout(request)
	request.session['has_logged_in'] = False
	request.session['username'] = ""
	
	return redirect('login') 
	#form = LoginForm(request.POST)
	#return render(request, 'projects/login.html' ,{'form' : form})
	
#project methods

@login_required(login_url='/login')
def project_add(request):
	if request.method == "POST":
		form = ProjectForm(request.POST)
		if form.is_valid():
			form.instance.start = datetime.date.today()
			form.instance.end = datetime.date.today()
			form.instance.added_by_user = request.user
			form.save()
			return redirect('project_list')
	else:
		form = ProjectForm()
	
	return render(request, 'projects/add.html', {"form": form})
	
def project_list(request):
	projects = get_list_or_404(Project.objects.order_by("name"))
	user = request.user
	message = 'logged in'
	
	#return render(request, 'projects/list.html', {"projects" : projects, "user" : user, "message" : message})

@login_required(login_url='/login')
def project_list(request, minigoal_id = None):
	user = request.user
	if minigoal_id:
		projects = get_list_or_404(Project.objects.filter(projectMinigoals = minigoal_id))
	else:
		projects = get_list_or_404(Project.objects.order_by('name'))
	minigoals = get_list_or_404(Minigoal.objects.order_by('name'))
	#if request.session['has_logged_in'] == True:
	#	message = 'logged in'
	#else:
	#	message = 'logged out'
	return render(request, 'projects/list.html', {"projects" : projects, "minigoals" : minigoals, "user" : user})
	
@login_required(login_url='/login')
def project_delete(request, project_id):
	project = get_object_or_404(Project, pk=project_id)
	project.delete()
	
	message = 'Project deleted '
	#return HttpResponse(message)
	return redirect("projects_for_user")
	#if project.added_by_user(request.user):
	#	project.delete()
	#	return redirect("projects_for_user")
	#else:
	#	return HttpResponse("Permission denied")

@login_required(login_url='/login')
def project_edit(request, project_id):	
	project = get_object_or_404(Project, pk=project_id)
	if request.method == "POST":
		form = ProjectForm(request.POST, instance = project)
		
		if form.is_valid():
			form.save()
			return redirect('project_list')
	else:
		form = ProjectForm(instance = project)
	return render(request, 'projects/edit.html', {"project" : project, "form" : form})

def projects_for_user(request):
	user = request.user
	projects = Project.objects.filter(added_by_user = user)
	return render(request, "projects/list.html", {"projects" : projects, "headline" : "Your projects", })

@login_required(login_url='/login')
def project(request, project_id):
	project = get_object_or_404(Project, pk=project_id)
	#project = Project.objects.get(id=1)
	user = request.user
	prusers = project.projectUsers
	#if user in prusers:
	#	hej = true
	#if user in project.projectUsers.all:
	#	canCreateMinigoal = true
	return render(request, 'projects/project.html', {"project" : project})
	
#user methods
@login_required(login_url='/login')
def users_list(request):	
	users = get_list_or_404(User.objects.order_by("first_name"))
	return render(request, 'users/list.html', {"users" : users})

@login_required(login_url='/login')
def user(request, user_id = None):
	user = User.objects.get(id=user_id) #change this
	
	if user_id:
		#userProjects = get_list_or_404(Project.objects.filter(projectMinigoals = minigoal_id))
		userProjects = Project.objects.filter(added_by_user = user)
		return render(request, 'users/user.html', {"user" : user, "userProjects" : userProjects})
	else:	
		user = User.objects.get(id=1) #Should be request user
	return redirect('users_list')
	
def user_edit(request):	
	return 
	
def user_delete(request):	
	return 

@login_required(login_url='/login')
def user_add(request):	
	form = UserForm(request.POST)
	if form.is_valid():
			form.save()
			return redirect('project_list')
	else:
		form = UserForm()
	return render(request, "users/add.html", {"form" : form}) 
	
#minigoal methods
@login_required(login_url='/login')
def minigoal_add(request):
	form = MinigoalForm(request.POST)
	if form.is_valid():
			#form.instance.start = datetime.date.today()
			#form.instance.end = datetime.date.today()
			#form.instance.added_by_user = request.user #might be form.instance.owner instead
			#form.instance.added_by_user = 
			form.save()
			return redirect('project_list')
	else:
		form = MinigoalForm()
	return render(request, "minigoals/add.html", {"form" : form})

@login_required(login_url='/login')	
def minigoal_create(request, project_id):
	project = get_object_or_404(Project, pk=project_id)
	user = request.user
	#user = 1 #should be user instead of integer
	form = MinigoalForm(request.POST)
	
	#project.ProjectUsers.all
	#for pruser in project.projectUsers.all:
	#	if user == pruser:
	#		pruser = validUser
	#if the pruser is valid, continue, else redirect the page. 
	#if user in project.projectUsers.all:
	#	canCreateMinigoal = true
	
	if form.is_valid():
			#form.instance.added_by_user = request.user #might be form.instance.owner instead
			form.instance.minigoalProject = project
			form.instance.minigoalOwner = user
			form.save()
			return redirect('project_list')
	else:
		form = MinigoalForm() #messages should be written
	return render(request, "minigoals/add.html", {"form" : form, "project" : project})

@login_required(login_url='/login')	
def minigoals_for_user(request):
	user = request.user
	projects = Project.objects.filter(added_by_user = user)
	return render(request, "projects/list.html", {"projects" : projects, "headline" : "Your projects", })

@login_required(login_url='/login')	
def minigoal_edit(request, minigoal_id):
	
	minigoal = get_object_or_404(Minigoal, pk=minigoal_id)
	minigoal.owner = minigoal.minigoalOwner
	user = request.user
	if minigoal.owner == user:
		if request.method == "POST":
			form = MinigoalForm(request.POST, instance = minigoal)
			
			if form.is_valid():
				form.save()
				return redirect('project_list')
		else:
			form = MinigoalForm(instance = minigoal)
		return render(request, 'minigoals/edit.html', {"minigoal" : minigoal, "form" : form})
	else:
		
		return HttpResponse("Permission denied") #Where to redirect from here, how to put link here to other page. Possibly just an errorpage

@login_required(login_url='/login')	
def minigoal_delete(request, minigoal_id):
	minigoal = get_object_or_404(Minigoal, pk=minigoal_id)
	minigoal.owner = minigoal.minigoalOwner
	user = request.user
	if minigoal.owner == user:
		minigoal.delete()
		return redirect("projects_for_user")
	else:
		#return redirect("projects_for_user")
		return HttpResponse("Permission denied")
	
	
#def project_delete(request, project_id):
#	project = get_object_or_404(Project, pk=project_id)
#	if project.added_by_user(request.user):
#		project.delete()
#		return redirect("projects_for_user")
#	else:
#		return HttpResponse("Permission denied")
