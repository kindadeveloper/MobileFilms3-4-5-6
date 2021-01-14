package ua.kpi.comsys.iv7101.mobilefilms.ui.movies

class FullMovie (val Title: String,
                 val Year: String,
                 val Rated: String,
                 val Released: String,
                 val Runtime: String,
                 val Genre: String,
                 val Director: String,
                 val Writer: String,
                 val Actors: String,
                 val Plot: String,
                 val Language: String,
                 val Country: String,
                 val Awards: String,
                 val Poster: String,
                 val imdbRating: String,
                 val imdbVotes: String,
                 val imdbID: String,
                 val Type: String,
                 val Production: String) {
    constructor(movie: Movie) : this(movie.Title, movie.Year, "", "", "",
        "", "", "","","","","","",
        "","","","",movie.Type,"")
    constructor() : this("", "", "", "", "",
        "", "", "","","","","","",
        "","","","","","")
}