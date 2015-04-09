from django.db import models
from django.contrib.auth.models import User
from django.utils.encoding import iri_to_uri
from django.forms import ModelForm
from django import forms

class UserForm(ModelForm):
	class Meta:
		model = User
		exclude = ('last_login', 'date_joined', 'groups', 'is_superuser', 'is_active', 'is_staff', 'user_permissions')
		#exclude = ('added_by_user')

class LoginForm(forms.Form):
	username = forms.CharField(max_length = 20)
	password = forms.CharField(max_length = 20, widget=forms.PasswordInput)

class Status(models.Model):
	name = models.CharField(max_length=50)
	#has_status = models.ForeignKey(Minigoal, related_name="minigoals")
	
	def __unicode__(self):
		return self.name
	


class Project(models.Model):
	name = models.CharField(max_length=50)
	description = models.CharField(max_length=50)
	start = models.DateField(max_length=50)
	end = models.DateField(max_length=50)
	projectUsers = models.ManyToManyField(User, related_name="projectUsers") #This does not work	
	added_by_user = models.ForeignKey(User, related_name="projects") 

	#added by user, watch demofilm. exclude it. 
	#owner = models.BigIntegerField(max_length=50)
	
	def __unicode__(self):
		return self.name #might as welll be description....
		
	def owned_by_user(self, user):
		return self.addad_by_user == user
		
	class Meta:
		permissions = (
			("can_add_projects_web", "can add projects throuth the web"), 
		)
		
class Minigoal(models.Model):
	name = models.CharField(max_length=50)
	minigoalStatus = models.ForeignKey(Status, related_name="minigoals") 
	minigoalOwner = models.ForeignKey(User, related_name="ownerMinigoals")
	minigoalProject = models.ForeignKey(Project, related_name="projectMinigoals") #What is this name projectMinigoals
	
		
	def __unicode__(self):
		return self.name

# Create your models here.

class MinigoalForm(ModelForm):
	class Meta:
		model = Minigoal
		#exclude = ('start', 'end')
		#exclude = ('added_by_user')
		exclude = ('minigoalProject', 'minigoalOwner')
		#exclude = ('minigoalOwner')
		
class ProjectForm(ModelForm):
	class Meta:
		model = Project
		exclude = ('start', 'end', 'added_by_user')
		#exclude = ('added_by_user')
		#How to exclude the owner from here. 
		

class SearchForm(ModelForm):
	class Meta:
		model = Project
		#exclude = ('start', 'end')
		#exclude = ('added_by_user')
		
