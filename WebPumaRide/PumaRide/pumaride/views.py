from django.shortcuts import render

# Create your views here.
def home(request):
	context = {}
	template = "index.html"
	return render(request,template,context)

def register(request):
	context = {}
	template = "pages/register.html"
	return render(request,template,context)

def maps(request):
	context = {}
	template = "pages/maps.html"
	return render(request,template,context)

def restore(request):
	context = {}
	template = "pages/restore.html"
	return render(request,template,context)

def terms(request):
	context = {}
	template = "pages/terms.html"
	return render(request,template,context)