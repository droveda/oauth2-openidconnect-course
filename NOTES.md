# Oauth 2 Course

## Cryptography Basics
In some scenarios, OAuth 2.0 protocol relies on tokens being sent from OAuth 2.0 Authorization Server to Application. When tokens are sent using HTTP, there is a need to encode it using Base 64 encoding as well as URL encoding. Messages are also Hashed and Digitally Signed using a Private Key. The same is true when using SAML 2.0 or any other Single Sign-On protocol. Time and again, I will refer to these concepts in the lectures and therefore a basic understanding of these topics is important.  

So, I would highly recommend that you take the 30 minutes tutorial of Cryptography basics provided in the Bonus section Cryptography Basics - Hashing, Encryption, Signatures which gives a very good overview of Base64 encoding, URL encoding, SHA256 Hashing, RSA Encryption and Digital Signatures.  

The examples and playground are inside the "crypto-play" folder.  

### URL Encoding
* URL encode
* URL decode
* Conform data to URL rules
* No spaces, special characters
* This is not encryption


### Base64 Encoding
Can be used to convert binary data to text and vice versa
* Base64 encode
* Base64 decode
* Binary to text encoding
* Embed binary data (images) in Text (html. xml)
* Send data as HTTP Post
* This is not encryption

### Hashing
The basic ideia of a cryptographic hashing function is that any data, no matter the size or the type, can be converted into a fixed size text representation.  
The fixed size representation is called a hash or a digest.  
The hash or the digest is unique to that data. There are no collisions.  
Data cannot be recovered from the hash.  
Password are usually stored as a hash in the identity management system and not as a encrypted password.  
You can use a hash to to make sure that a downloaded file is not corrupted and nobody has tampered the contents.  
Digital signatures.  

* One Way
* No collisions
* It's not Encryption

#### Hash Algorithm
* SHA-256
* SHA-512

### Symmetric Encryption
Encrypt and Decrypt using a Secret private key.  
* Some algorithm
  * AES-256
  * AES-128
  * BlowFish
  * DES
* Simple
* Secret Key
* Efficient for large data
* Hard to share secret key


### Asymmetric Encryption
* Private Key - The private key is always keept with the owner and in a safe place. This key basically represents the owner. The owner can digitally sign the data using his private key
* Public Key - Can freely be distributed, anyone can know about that.

RSA key pair.  
* If you encrypt the data using the public key, the decryption can only be done using the private key.
* If you sign the data using the private key, then the verification can only be done using the corresponding public key.

#### Tips
* Encrypt with Public Key, Decrypt with Private Key
* Sign with Private Key, verify with Public Key
* IN RSA - Data size cannot be more than the Key Size

#### Public Key Certificates
* Public Key Certificate (x.509)
* Certificate Authority (CA)
Public keys need to be certified, otherwise no one can trust anyone's public key.  
Whenever some entity wats to create a RSA key pair, this Key Pair is generated using a simple command, say openssl.  
The public key is sent to the certificate authority. Maybe VeriSign, along with all the information that is necessary for the verification of the entity (Drivers License, Corporation Information, etc...)  
The certificate authority will do whatever it can to verify the public key and the entity.    
Finally, the CA will provide the entity with a certificate for his public key.  
This certificate contains the public key and is signed by the certificate authority's private key.  
You can also have a certificate chain with intermediate CAs as well.  
So in reality, Alice and Bob would exchange public key certificates and not public keys themselves.  
For testing purposes, you can actually create self-signed certificates where there is no certificate authority, in that case your public key certificate can be signed with your own certificate. You become your own CA. Howerver, this should be used only for testing, and no browsers will actually consider this as a legitimate CA.  


### Hybrid Encryption
Both symmetric and asymmetric encryption are used.  
The random secret key can be encrypted using the RSA public key of the destination.  
And this RSA encrypted key can be sent along with the AES encryoted data as a single package to the destination.  

![Hybrid Encryption](/images/hybrid.png)

#### Simple scenario:
1. Alice already has the public certificate of Bob, so indirectly she has Bob's public key
2. Alice will generate a random secret key and use AES or any symmetric encryption to encrypt the message that she wants to send.
3. After that, she will use Bob's public key to encrypt the random secret key to generate the cipher key
4. Once that is completed, both the AES Cipher data and the RSA cipher key will be sent as a package to Bob's side.
5. On the other side, Bob will use his RSA private key to decrypt the RSA cipher key, basically to extract the dynamic AES secret key that was generated by Alice.
6. And finally, Bob will use the extracted secret key to decrypt the AES cipher data and get the actual data.

### Digital Signatures and Verification

![Hybrid Encryption](/images/digital-signature.png)

1. Alice will first create a hash or a digest of the data (SHA-256)
2. Alice will then sign the hash with her private key, and that will create a signature for the entire document.
3. Both the data and the signature are sent over Bob's side, maybe using a HTTP post or any other means. IN SAML, for example, both the data and the signature are combined into a single XML file and sent using HTTP POST. (For OAuth 2.0, JSON **Header** and **Payload** are signed and made part of the **JWT** Token. The signature is part of the JWT Token)
4. The hashing algorithm is also sent as a part of this.
5. Bob will then get the data part out of it and create a SHA256 hash. Lets' call this **data hash**.
6. He will then take the signature component and then apply the verify function to it, which is the same as Decrypt function. But he will use Alice's public key to get the hash back, lets' call this the **sign hash**.
7. If Bob finds that the **data hash** is the same as the **sign hash**, then he is confident that it is Alice who has sent this message. Remember that a particular hash can only be generated from a particular Data. (The signature is considered "verified" if the Data Hash is the same as the Sign Hash) (For Oauth 2.0 we will be talking about Signatures but not Encryption because its not used often)

