from django import template

register = template.Library()

@register.filter(name='ownership')
def ownership(project, user):
	return project.added_by_user == user