import MovieType from "../../../../../Type/MovieType.tsx";

export default function ListElement({ movie }: { movie: MovieType }) {
    return (
        <div>

            {movie.name}  {/* Use the movie object directly */}
        </div>
    );
}
