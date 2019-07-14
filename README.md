# Coding Exercise for NOTHS
 

2 solutions available:
## first solution on master branch

Got a first working solution on working_solution tag and refactored from there.

Did my best to avoid over engineering and adding unrequired features. 

One feature I added though is the possibility to have multiple promotions on total (15% if >80pounds, 10% if >60pounds).

## more sophisticated architecture on refactoring branch (with repository layer)

Tried starting an n-tier architecture (service-repository-domain) to prepare the ground work for the use of a database, 
and assumed a checkout API would be called later by a front-end application. 

We would have to replace the memory repository implementation with an implementation for the database of your choice. 

But I quickly found my implementation clumsy so didn't proceed further. I found it difficult since the given interface 
for Checkout cannot be taken as a Service and mixes both functions including logic and holds data (promotional rules in constructor). 

I didn't put a basket identifier in the scannedProduct object (nor manage it in repository functions) since it could do without at this stage.

## next steps

To carry on, I would  
* gather more user requirements from your end (do you want to store the user's basket to resume later, etc), 
* gather more technical requirements (do you hold a user session on backend or is it stateless, etc) 
* possibly change the interface to the following:

```
CheckoutService service = new CheckoutService();
String checkoutId = service.startCheckout(promotionalRules);
service.scan(checkoutId ,"001");
service.scan(checkoutId ,"002");
Double price = service.getTotal(checkoutId);
```

