# Hooks

This folder contains hooks needed for encryption and decryption.

## repo_init.sh
* These hooks are configured by running `./repo_init.sh` by copying these hooks to your local `.git/hooks` folder.
* It decrypts all the files in `credentials` folder.
* The encrypted files contain the key for encryption. It picks the key from first occurrence of the encrypted files and replaces string `mozillaSopsGcpKey` in the `pre-commit` hook in `.git/hooks` folder.

## pre-commit
* Triggers just before a commit.
* This hook makes sure no one commits the files with extension other than `.md`, `.enc` and `.gitignore` that are in the `credentials/` folder.
* It compares the existing decrypted data and encrypts and stages if new data in decrypted files is found.

## post-checkout
* Triggers just after a checkout.
* It decrypts the encrypted files in `credentials/` folder

## post-merge
* Triggers just after a merge.
* It decrypts the encrypted files in `credentials/` folder

## pre-push
* Triggers just before a push.
* It decrypts the encrypted files in `credentials/` folder
