#!/usr/bin/env bash

# ensure it runs off the right directory
cd "$( dirname "$0" )"

# replace git hooks with your git hooks
cp -f ./post-* ./pre-* ../.git/hooks/
echo "copied hooks"

# code to decrypt all files in credentials

# Iterate over all .enc files in credsenc folder
for enc_file in `ls -1 ../credentials/*.enc 2>/dev/null`; do
  # Extract filename without the extension
  filename=$(basename "$enc_file" .enc)
  # Decrypt the file to the corresponding file in credentials folder
  sops --decrypt "$enc_file" > "../credentials/$filename"
done

filename="../.git/hooks/pre-commit"

# Set the path to the credentials folder
credentials_path="../credentials"

# Define the regular expression to search for the resource ID
resource_regex='"resource_id":\s*"([^"]+)"'

# Use grep to extract the resource ID from a file in the credentials folder
resource_line=$(grep -hr -E "$resource_regex" $credentials_path | head -1)

echo "Matching line: $resource_line"

# Use sed to extract the resource ID from the matching line
resource_id=$(echo $resource_line | sed -E "s/$resource_regex/\1/" | tr -d '",')

echo "Resource ID: $resource_id"

sed -i "s|mozillaSopsGcpKey|$resource_id|g" "$filename"
