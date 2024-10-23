import MovieRatingType from "../../../../../Type/MovieRatingType.tsx";

export default function ListElement({ movie }: { movie: MovieRatingType }) {
    return (
        <div>
            {movie.movieName}  {/* Use the movie object directly */}
        </div>
    );
}
