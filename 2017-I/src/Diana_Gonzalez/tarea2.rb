# Excepciones.
# 			Técnica básica de manejo de errores.

arr = [1,2,'a',4,'b']
for i in arr	
	begin
		a = 0+i
		rescue StandardError=>e
   			puts "Error: #{e}."						#Descripción del error.
   		else										#Si no hay error, se ejecuta.
   			puts "The sum is: #{a}."
   		ensure										#Siempre se ejecuta, haya error o no.
   			puts "I'm awesome."
	end
end


for i in 1...4
  retries = 2
  begin 											#Inicia el bloque de manejo de excpeciones. Dentro van las operaciones propensas a fallo.
  	a = 2**i
    puts "Result: #{a}"
    raise "Exception in: #{i}" 						#Excepción ocurre.
  rescue Exception=>e 								#Entra cuando encuentra una excepción y la guarda, en vez de Exception puede ser StandardError u otro.
    puts "Caught: #{e}"								#Descripción de la excepción.
    if retries > 0
      puts "Trying #{retries} more times"	
      retries -= 1
      retry											#Vuelve a intentar desde begin.
    end  
  end
end

#Iteradores.

# Con each.
('A'..'Z').each do |letter|							#El iterador toma el valor y lo guarda en la variable "letter".
  print "#{letter} "
end

#Con collect.
a = [1,2,3,4,5]
b = a.collect{|x| 10*x}
puts b

#Con enumerator.
#When the each method is called and no block is provided,
# it returns an enumerator object, which is an instance of the Enumerator class.

e =  [ 1, 2, 3, 4, 5 ].each  # Salida: => #<Enumerator: [1, 2, 3, 4, 5]:each>
puts e.size
puts e.next
puts e.next
puts e.peek						#Muestra el siguiente elemento sin mover la posición
e.rewind						#Regresa a la posición del primer elemento
puts e.next

#Generadores.

my_array = ['v1', 'v2']

my_generator = Enumerator.new do |yielder|
    index = 0
    loop do
        yielder.yield(my_array[index])
        index += 1
    end
end

puts my_generator.next   
puts my_generator.next    
puts my_generator.next