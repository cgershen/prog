class Animal 
	def initialize(type, name, age)
		@type = type.capitalize
		@name = name.capitalize
		@age = age
	end

	def eat(food)
		puts "I'm eating #{food}."
	end

	def get_data
		puts "Hi, I'm a #{@type}, my name is #{@name} and I'm #{@age} years old."
	end

end

class Dog < Animal
	def bark 
		puts "Woof, woof."
	end
end

class Monkey < Animal
	def talk
		puts "I'm a monkey so I'm super smart."
	end
end

obj = Dog.new("dog", "akane", 2)
obj.bark
obj.get_data

obj1 = Monkey.new("monkey", "diana", 3)
obj1.get_data
obj1.eat("fruit")
obj1.talk