#!/bin/bash

# Script to renew expired GPG key for Maven publishing
# GPG Key: 7A8C9502013DE855

set -e

KEY_FINGERPRINT="7E1AAE5F71102905F2A5942F7A8C9502013DE855"
KEY_ID="7A8C9502013DE855"
EXPIRATION="2y"  # Change this to desired expiration (e.g., 2y, 5y, or 0 for no expiration)

echo "=========================================="
echo "GPG Key Renewal Script"
echo "=========================================="
echo "Key ID: $KEY_ID"
echo "New Expiration: $EXPIRATION"
echo ""

# Check current key status
echo "Current key status:"
gpg --list-secret-keys --keyid-format=long | grep -A 2 "$KEY_ID" || true
echo ""

# Renew the key expiration (non-interactive)
echo "Extending key expiration to $EXPIRATION..."
gpg --quick-set-expire "$KEY_FINGERPRINT" "$EXPIRATION"

# Also update all subkeys
echo "Updating subkeys expiration..."
gpg --quick-set-expire "$KEY_FINGERPRINT" "$EXPIRATION" '*'

echo ""
echo "✓ Key expiration updated successfully!"
echo ""

# Verify the update
echo "Updated key status:"
gpg --list-secret-keys --keyid-format=long | grep -A 2 "$KEY_ID"
echo ""

# Ask about publishing to keyserver
read -p "Do you want to publish the updated key to keyserver? (y/n) " -n 1 -r
echo ""
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "Publishing key to keyserver.ubuntu.com..."
    gpg --keyserver keyserver.ubuntu.com --send-keys "$KEY_ID"
    echo "✓ Key published successfully!"
fi

echo ""
echo "=========================================="
echo "Done! You can now run Maven deploy:"
echo "  mvn clean deploy -P release"
echo "=========================================="