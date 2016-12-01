function makeIterator(array){
    var nextIndex = 0;
    return {
       next: function(){
           return nextIndex < array.length ? { value: array[nextIndex++], done: false } : { done: true };
       }
    }
}

var it = makeIterator(['uno', 'dos']);
console.log(it.next().value); // 'uno'
console.log(it.next().value); // 'dos'
console.log(it.next().done);  // true

function* idMaker(){
  var index = 0;
  while(true)
    yield index++;
}

var gen = idMaker();

console.log(gen.next().value); // 0
console.log(gen.next().value); // 1
console.log(gen.next().value); // 2



//referencia https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Iterators_and_Generators

function TryCatch(x) {
    try { 
        if(x == "") throw "is empty";
        if(isNaN(x)) throw "is not a number";
        x = Number(x);
        if(x > 10) throw "is too high";
        if(x < 5) throw "is too low";
    }
    catch(err) {
        console.log("Error: " + err + ".");
    }
}
TryCatch();
TryCatch("a");
TryCatch(2);
TryCatch(7);
TryCatch(9);