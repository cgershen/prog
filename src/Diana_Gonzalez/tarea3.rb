require 'thread'

@mutex = Mutex.new
@x = 1000000

def helloWorld(id)
	@mutex.synchronize do
		puts "Hello, world! from thread #{id} at time #{Time.now}"
		sleep(1)
		Thread.current["x"] = @x
		@x -= Random.rand(1..5000)
	end
end

threads = []

10.times do
	threads << Thread.new{helloWorld(Thread.current.object_id)}	
end

threads.each do |i|
	i.join 
	@mutex.synchronize do
		puts "I'm thread #{i.object_id} and my x was #{i["x"]}"
	end
end
