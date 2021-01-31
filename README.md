# Music Wiki

MusicWiki is an unofficial Last.fm app that contains information about different music genres,
the albums, artists and tracks listed under the genre.

<p align="center" width="100%">
    <img width="33%" src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/test.gif"> 
</p
<br />

<br />

## Screenshots 
<img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/main1.png" width="200"> <img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/main2.png" width="200"> <img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/detailsone.png" width="200"> <img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/detailstwo.png" width="200">
<img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/detailsthree.png" width="200"> <img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/detailfive.png" width="200"> <img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/detailsix.png" width="200"> <img src="https://github.com/rawlin/Music-Wiki/blob/master/screenshots/artist_details.png" width="200">
### Some Details about the app
- The app has 4 main screens which are as TopGenres, GenreDetails, AlbumDetails and ArtistDetails
- Architecture used is MVVM(Model-View-View-Model) with Repository pattern
- Other significantly used components are RecyclerViews, ViewPager, Retrofit

### TopGenres
- First screen of the app with a `RecyclerView` with `GridLayoutManager`
- Displays only 12 Genres at first, displays the rest after pressing the down arrow
### GenreDetails
- Layout consists of a `ViewPager` with `TabLayout` along with a view above it to show Genre details
- 3 `Fragments` are interchanged between the `ViewPager` which themselves contain a `RecyclerView` to show list of albums, artists and songs
### AlbumDetails
- Image of album and a horizonatlly scrolling `RecyclerView`
- Clicking on the Genre items takes you to the GenreDetails Screen
### ArtistDetails
- Has 3 `RecyclerViews`(Top Albums, Top Tracks, Genres) which are all horizontally scrolling

## Libraries Used
- [Retrofit](https://github.com/square/retrofit) - REST client 
- [OkHttp](https://github.com/square/okhttp) - Http client and for logging network calls
- [Glide](https://github.com/bumptech/glide) - Image loading library
- [Gson](https://github.com/google/gson) - Json serializer/deserializer
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Kotlin library for running asynchronous work
