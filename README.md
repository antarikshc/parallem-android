<h1 align="center">parallem</h1>

<p align="center">
  <a href="https://developer.android.com/studio/releases/platforms#4.4"><img alt="API 19" src="https://img.shields.io/badge/API-19%2B-brightgreen"/></a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0"><img alt="Apache License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>


<p align="center">  
Parallem is a collaborative platform for developer, designer and techstars based on Android's MVVM architecture.
</p>
</br>

<p align="center">
<img src="assets\02-SignUp.png" width="32%" />
<img src="assets\04-Dashboard.png" width="32%" />
<img src="assets\05-PersonalDetails.png" width="32%" />
<img src="assets\07-Profile.png" width="32%" />
</p>

## Built with
- Minimum SDK level 19
- Android Jetpack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Volley](https://github.com/google/volley) - communicate the REST APIs
- [GSON](https://github.com/google/gson) - Java serialization/deserialization library to convert Java Objects into JSON and back
- [Picasso](https://github.com/square/picasso) - Image downloading and caching library
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView

## Architecture
Parallem is based on MVVM architecture and a repository pattern.

<p align="center"><img src="assets\arch.png" width="80%"/></p>

# License
```xml
Designed and developed by 2018 antarikshc (Antariksh Chavan)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```