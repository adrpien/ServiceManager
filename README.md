# service_manager

service manager is an app supporting the documentation of repairs and technical inspections performed by medical equipment service employees.  
app was organized in multi modules with usage of  mvvm and clean architecture. ui is built with jetpack compose. 
created using: firebase, dependency injection, coroutines, room database, shared preferences and more. 

## installation

to install service manager, follow these steps:

1. clone the repository:
   https://github.com/adrpien/ServiceManager.git
3. open the project in Android Studio.
4. build and run the app on your device or emulator.

## usage

minSdk = 26

## modules

- app: [contains main activity, theme, navigation]
- core: [contains components used across whole app, e.g. util and helper classes, extension functions]
- core_ui: [contains composable components and related classes used across whole app]
- common: [contains whole business logic to create, fetch, update and cache data common for feature home, inspections and repairs]
- feature_inspections: [contains whole business logic and ui layer to create, fetch, update and cache inspections data]
- feature_repairs: [contains whole business logic and ui layer to create, fetch, update and cache repairs data]
- feature_authentication: [contains whole business with ui layer for authentication process]
- featue_home: [implements mechanisms to manage app settings and database settings]
- logger: [contains whole business logic for firebase logger]
- shared_preferences: [contains whole business logic for shared_poreferences]
- test: [contains components used in tests across whole app e.g. fake ropositories]

## contributing


if you'd like to contribute to service please follow these guidelines:
- clone service manager repository
- view todo items across whole app and choose one or suggest new development directions via e-mail to create one
- fork the repository.
- create a new develop branch based branch for your feature or bug fix
- make your changes and commit them
- push to your branch.
- create a pull request to merge your changes into the develop branch of the original repository.

## license

this software is licensed under the gnu general public license (gpl) version 3.0 or later. you may obtain a copy of the license at https://www.gnu.org/licenses/gpl-3.0.html

## credits


## contact

adrpien@gmail.com
