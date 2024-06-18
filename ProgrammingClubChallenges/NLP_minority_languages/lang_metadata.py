from datasets import load_dataset

african_languages = {
    "en": "English",
    "am": "Amharic",
    "ber": "Berber",
    "bam": "Bambara",
    "ff_pulaar": "Pulaar",
    "ff_fulfulde": "Nigerian Fulfulde",
    "ha": "Hausa",
    "ig": "Igbo",
    "kab": "Kabyle",
    "kam": "Kamba (Kenya)",
    "lg": "Ganda",
    "ln": "Lingala",
    "mg": "Malagasy",
    "mos": "Morisyen",
    "rn": "Kirundi",
    "rw": "Kinyarwanda",
    "sh": "Shona",
    "so": "Somali",
    "sw": "Swahili (individual language)",
    "tgq": "Tachawit",
    "ti": "Tigrinya",
    "ts": "Tsonga",
    "tn": "Tswana",
    "umb": "Umbundu",
    "wo": "Wolof",
    "xh": "Xhosa",
    "yo": "Yoruba",
    "zu": "Zulu"
}
langs_nums = {}

def generate_language_data():
    # Iterate over all n choose 2 language pairs
    for lang in african_languages.keys():
        other_langs = [l for l in african_languages.keys() if l != lang]
        for second_lang in other_langs:
            print(f'First lang is {lang} and second lang is {second_lang}')
            try:
                dataset = load_dataset("tatoeba", lang1=second_lang, lang2=lang, streaming=True)  # Only get metadata and don't actually download the dataset
                num_rows = sum(1 for _ in dataset['train'])
                
                val = african_languages.get(lang) + ", " + african_languages.get(second_lang)
                print(f'Number of rows for {val} is {num_rows}')
                langs_nums[val] = num_rows
            except FileNotFoundError:
                print(f"Dataset not found for {lang}-{second_lang} pair.")
            except Exception as e:
                print(f"An error occurred for {lang}-{second_lang} pair: {e}")

    print(langs_nums)
    with open('/Users/irislitiu/work/misc-coding-challenges/ProgrammingClubChallenges/NLP_minority_languages/tatoeba_dataset_langs.txt', 'w+') as f:
        out = ''.join([lang + " : " + str(langs_nums[lang]) + '\n' for lang in langs_nums.keys()])
        f.write(out)

generate_language_data()
