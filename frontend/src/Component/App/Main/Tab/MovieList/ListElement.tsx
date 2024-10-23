import MovieType from "../../../../../Type/MovieType.tsx";

export default function ListElement({ movie }: { movie: MovieType }) {
    return (
        <div>
            {movie.movieName}  {/* Use the movie object directly */}
        </div>
    );
}
