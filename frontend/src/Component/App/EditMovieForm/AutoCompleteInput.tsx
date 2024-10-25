import {ChangeEvent, useState} from 'react';
import PersonType from "../../../Type/PersonType.tsx";

const AutoCompleteInput = ({suggestions}: { suggestions: PersonType[] }) => {
    const [inputValue, setInputValue] = useState<string>('');
    const [personId, setPersonId] = useState<string>("");
    const [filteredSuggestions, setFilteredSuggestions] = useState<PersonType[]>([]);
    const [showSuggestions, setShowSuggestions] = useState(false);

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const userInput = event.target.value;
        setInputValue(userInput);

        const filtered = suggestions.filter(suggestion =>
            suggestion.name.toLowerCase().startsWith(userInput.toLowerCase())
        );

        setFilteredSuggestions(filtered);
        setShowSuggestions(true);
    };

    const handleClick = (suggestion: PersonType) => {
        setInputValue(suggestion.name);
        setPersonId(suggestion.id);
        setFilteredSuggestions([]);
        setShowSuggestions(false);
    };

    return (
        <div>
            <input
                type="text"
                value={inputValue}
                onChange={handleChange}
                onFocus={() => setShowSuggestions(true)}
            />
            {showSuggestions && inputValue && (
                <ul>
                    {filteredSuggestions.map((suggestion, index) => (
                        <li key={index} onClick={
                            () => {
                                handleClick(suggestion)
                            }
                        }>
                            {suggestion.name}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default AutoCompleteInput;
