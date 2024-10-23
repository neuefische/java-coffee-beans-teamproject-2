import MovieRatingType from "../../../../../Type/MovieRatingType.tsx";

export default function ListElement({movie}: { movie: MovieRatingType }) {
    return (
        <>
            <div className={"list-element"}>
                <span className={"movie-name"}>{movie.movieName}</span>
                <span className="movie-rating">
  {"★".repeat(movie.rating)}
                    <span className="empty-stars">
    {"★".repeat(10 - movie.rating)}
  </span>
</span>
                <button className={"edit-button"}>Edit</button>
            </div>
        </>
    );
}
