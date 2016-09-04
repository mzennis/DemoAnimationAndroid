# DemoAnimationAndroid
Sample Code Animation, required API 21

```java
// Activity Transition
// fade
Fade enterTransition = new Fade();
enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));

// explode
Explode enterTransition = new Explode();
enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long

// slide 
Slide enterTransition = new Slide();
enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
````

```java
//Shared Elements
ViewCompat.setTransitionName(view.findViewById(R.id.img_profile), "sebastian");
ViewCompat.setTransitionName(view.findViewById(R.id.img_profile1), "rezeki");
```
