from django.http import HttpResponse,HttpResponseRedirect
from .models import Place
from django.shortcuts import render,get_object_or_404,redirect

# Create your views here.
def home(request):
	queryset = Place.objects.all()
	print queryset
	context = {
	"points":queryset,
	}
	return render(request,"base.html",context)