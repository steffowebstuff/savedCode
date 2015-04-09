from django.conf.urls import patterns, include, url

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    #url(r'^$', 'djangoproject.views.home', name='home'),
    # url(r'^djangoproject/', include('djangoproject.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
	
    url(r'^admin/', include(admin.site.urls)),
	
	#projects
	url(r'^projects/$', 'myapp.views.project_list', name="project_list"),
	url(r'^projects/minigoal/(?P<minigoal_id>\d+)/$', 'myapp.views.project_list', name="project_list_with_minigoal"),
	url(r'^project/(?P<project_id>\d+)/delete/$', 'myapp.views.project_delete', name="project_delete"),
	url(r'^project/(?P<project_id>\d+)/edit/$', 'myapp.views.project_edit', name="project_edit"),
	url(r'^project/(?P<project_id>\d+)/$', 'myapp.views.project', name="project"),
	url(r'^project/add$', 'myapp.views.project_add', name="project_add"),	
	url(r'^projects/user/$', 'myapp.views.projects_for_user', name="projects_for_user"),	
	url(r'^projects/minigoal/(?P<minigoal_id>\d+)/$', 'myapp.views.project_list', name="project_list_with_minigoal"),
	url(r'^projects/user/$', 'myapp.views.projects_for_user', name="projects_for_user"),	
	url(r'^projects/user/$', 'myapp.views.projects_for_user', name="projects_for_user"),
	
	url(r'^login/$', 'myapp.views.login_user', name="login"),
	url(r'^logout/$', 'myapp.views.logout_user', name="logout"),
	url(r'^search/$', 'myapp.views.search', name="search"),
	url(r'^permission/error/$', 'myapp.views.error_permission', name="error_permission"),
	
	url(r'^minigoals/(?P<minigoal_id>\d+)/$', 'myapp.views.minigoals_for_user', name="minigoals_for_user"),
	url(r'^minigoals/add$', 'myapp.views.minigoal_add', name="minigoal_add"),
	url(r'^minigoal/edit/(?P<minigoal_id>\d+)/$', 'myapp.views.minigoal_edit', name="minigoal_edit"),
	url(r'^minigoal/create/(?P<project_id>\d+)/$', 'myapp.views.minigoal_create', name="minigoal_create"),
	url(r'^minigoal/delete/(?P<minigoal_id>\d+)/$', 'myapp.views.minigoal_delete', name="minigoal_delete"),
	
	#user methods
	url(r'^users/$', 'myapp.views.users_list', name="user_list"),
	url(r'^users/add$', 'myapp.views.user_add', name="user_add"),
	url(r'^user/(?P<user_id>\d+)/edit/$', 'myapp.views.user_edit', name="user_edit"),
	url(r'^user/(?P<user_id>\d+)/$', 'myapp.views.user', name="user"),
	
)
