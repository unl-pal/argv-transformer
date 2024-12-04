# future.sample
CallableFuture is implementation of Future that returns value in .get() only when .call(U arg) method is called with argument.
This makes CallableFuture useful to notify code in separate threads about results of work of another threads.

Create it with:
```
final CallableFuture<String, Integer> callableFuture = new CallableFuture<String, Integer>(new CallableFuture.CallableWithArg<String, Integer>() {
            @Override
            public String call(Integer integer) {
                return "\"resulting status is "+integer+"\"";
            }
        });
```
        
wait for result (blocking) with:
```
callableFuture.get()
```

call from anywhere with result and ublock previous .get():
```
callableFuture.call(66);
```

