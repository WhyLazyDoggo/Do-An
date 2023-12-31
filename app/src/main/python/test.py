from typing import Tuple, Optional
from binascii import unhexlify
import hashlib
import os
import math, random
#Check unidecode


# Elliptic curve parameters
p = 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F
n = 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141
G = (0x79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798,
     0x483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8)

# Points are tuples of X and Y coordinates
# the point at infinity is represented by the None keyword
Point = Tuple[int, int]


def checkvefi(msg, pubkey, signature):
    if not schnorr_verify(str_to_bytes(msg), str_to_bytes(pubkey), str_to_bytes(signature)):
        return "hong"
    else:
        return "dung roi"

def getpublickey(priKey):
    privkey_int = int_from_hex(priKey)
    publickey = pubkey_point_gen_from_int(privkey_int)
    # Kiểm tra nếu privkey là số chẵn thì biến đổi, số lẻ giữ nguyên
    privkey_even = privkey_int if has_even_y(publickey) else n - privkey_int
    hex_privkey = hex(privkey_even).replace('0x', '').rjust(64, '0')
    print("Privatekey cuối cùng = ",hex_privkey) #In ra khóa bí mật thôi không có ý nghĩa gì lắm
    return bytes_from_point(publickey).hex()

def getprivatekey(username):
    t = xor_bytes(bytes_from_int(30112001), tagged_hash("BIP0340/aux", get_aux_rand()))
    ki = int_from_bytes(tagged_hash("BIP0340/nonce", t)) % n

    privkey_int = int_from_hex(hashlib.sha256((str(ki)+username).encode()).hexdigest())
    privkey_even = privkey_int if has_even_y(pubkey_point_gen_from_int(privkey_int)) else n - privkey_int
    hex_privkey = hex(privkey_even).replace('0x', '').rjust(64, '0')
    return hex_privkey

def resetPassword(name):
    # Remove accents from usernames
    initials = ""
    removeAccent = name.split()
    i=0
    for word in removeAccent:
        initials += word[0]
    # Random one number to add to password
    val = str(random.randint(100, 999))
    # Add numbers to the beginning of the username and reverse the string
    new_str_list = list(initials)
    new_str_list.insert (0, val)
    new_str_list.reverse()
    new_str = ''.join(new_str_list)
    # Remove whitespace
    new_passwd = new_str.replace(" ", "")
    return new_passwd

def createSignature(array,msg,X,R):
    sSum = 0
    string_list = array.split()
    phantu = [int(x.strip()) for x in R.strip('()').split(',')]  # bước 3
    Rs = tuple(phantu)  # bước 4
    for u in string_list:
        u = int(u)
        sSum += u % n
    sSum = sSum % n
    print("Giá trị sSum là:",sSum)
    signature_byte = bytes_from_point(Rs) + bytes_from_int(sSum)
    print(signature_byte.hex())
    if not schnorr_verify(str_to_bytes(msg), bytes_from_point(lift_x_even_y(str_to_bytes(X))), signature_byte):
        print("Lỗi không qua hệ thống xác thực, thì sao update được...?")
        raise RuntimeError('The created signature does not pass verification.')
    return signature_byte.hex()

def createSi(di,c,ki,L,Xx,Rsumm):



    ki_process = int(ki)

    di = int_from_hex(str_to_bytes(di).hex())
    Pi = pubkey_point_gen_from_int(di)
    ai = int_from_bytes(sha256(str_to_bytes(L) + bytes_from_point(Pi)))

    phantu = [int(x.strip()) for x in Xx.strip('()').split(',')]  # bước 3
    X = tuple(phantu)  # bước 4

    # The aggregate public key X~ needs to be y-even
    if not has_even_y(X):
        ai = n - ai

    # If the aggregated nonce does not have an even Y
    # then negate  individual nonce scalars (and the aggregate nonce)

    phantu = [int(x.strip()) for x in Rsumm.strip('()').split(',')]  # bước 3
    Rsum = tuple(phantu)  # bước 4

    if not has_even_y(Rsum):
        ki_process = n - ki_process


    si = (di * int(c) * ai+ ki_process) % n
    return si

def hashMsg_type_2(M):
    return sha256(M.encode()).hex()

def createC(Rsumm,X,msg):
    phantu = [int(x.strip()) for x in Rsumm.strip('()').split(',')]  # bước 3
    Rsum = tuple(phantu)  # bước 4

    print("Tham số msg trong này là: ",msg)
    print(type(msg))
    print(tagged_hash("BIP0340/challenge", (bytes_from_point(Rsum) + bytes_from_point(lift_x_even_y(str_to_bytes(X))) + str_to_bytes(msg))))

    c = int_from_bytes(tagged_hash("BIP0340/challenge", (bytes_from_point(Rsum) + bytes_from_point(lift_x_even_y(str_to_bytes(X))) + str_to_bytes(msg)))) % n
    return c


def createRSum(arrayy):
    array = list(map(int, arrayy.split()))
    Rsum = None
    for ki in array:
        Ri = point_mul(G, ki)
        assert Ri is not None

        # Rsum = R1 + ... + Rn
        Rsum = point_add(Rsum, Ri)
    return Rsum

def hashMsg(M):
    return sha256(M.encode())

def checktype(array):
    print(type(array))
    print(array)
    list_of_elements = array.split()
    for u in list_of_elements:
        print(u)
    print(type(list_of_elements))


def createKi(pub_key, msg):
    Pi = lift_x_even_y(str_to_bytes(pub_key))
    ki= 0
    while(ki == 0):
        t = xor_bytes(bytes_from_int(n), tagged_hash("BIP0340/aux", get_aux_rand()))
        ki = int_from_bytes(tagged_hash("BIP0340/nonce", t + bytes_from_point(Pi) + msg.encode())) % n
        Ri = point_mul(G, ki)
        assert Ri is not None
    return ki

def creatXpoint(arrayy):

    array = arrayy.split()

    L = b''
    for u in array:
        L += str_to_bytes(u)
    L = sha256(L)
    X = None

    for u in array:
        # Get private key di and public key Pi
        di = int_from_hex(u)
        if not (1 <= di <= n - 1):
            raise ValueError('The secret key must be an integer in the range 1..n-1.')
        Pi = lift_x_even_y(str_to_bytes(u))
        assert Pi is not None
        # KeyAggCoef
        # ai = h(L||Pi)
        ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))

        # Computation of X~
        # X~ = X1 + ... + Xn, Xi = ai * Pi
        X = point_add(X, point_mul(Pi, ai))

    return X


def creatX(arrayy):

    array = arrayy.split()

    L = b''
    for u in array:
        L += str_to_bytes(u)
    L = sha256(L)
    X = None

    for u in array:
        # Get private key di and public key Pi
        di = int_from_hex(u)
        if not (1 <= di <= n - 1):
            raise ValueError('The secret key must be an integer in the range 1..n-1.')
        Pi = lift_x_even_y(str_to_bytes(u))
        assert Pi is not None
        # KeyAggCoef
        # ai = h(L||Pi)
        ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))

        # Computation of X~
        # X~ = X1 + ... + Xn, Xi = ai * Pi
        X = point_add(X, point_mul(Pi, ai))

    return bytes_from_point(X).hex()

def creatL(arrayy):
    array = arrayy.split()

    L = b''
    for u in array:
        L += str_to_bytes(u)
    L = sha256(L)

    return L.hex()

def getHello():
    print("Gimme Herro")
    return "this is ket qua tra ve"

def get_pub_point_form_int(pubkey: int) -> Point:
    P = point_mul(G, pubkey)
    assert P is not None
    return P

def str_to_bytes(pubkey: hex) -> bytes:
    pubkey = bytes.fromhex(pubkey)
    return pubkey

def checkCreatL(msg: bytes, users: list) -> bytes:

    #Kiểm tra msg

    if len(msg) != 32:
        raise ValueError('The message must be a 32-byte array.')

    #Tạo khóa chung nè
    # Key aggregation (KeyAgg), L = h(P1 || ... || Pn)
    L = b''

    #Nhét publickey vào đây dùm
    for u in users:
        L += pubkey_gen_from_hex(u["privateKey"])      #Vcl thế khoa công khai lồn này làm gì vậy
        #Khóa được tạo ra từ mã hex KBM
        #Sử dụng dữ liệu khóa công khai thôi, và đây là dữ liệu bytes
        #Chắc là lưu KCK dưới dạng bytes trong db
        print("Từng giá trị L tính ra được: ", L.hex())
    L = sha256(L)
    print("Giá trị L tính ở bên trong hàm là: ",L.hex())

def createL(array: hex):
    L = b''
    for u in array:
        L += str_to_bytes(u)
    L = sha256(L)
    return L


def createXi(pubkey,L):
    Pi = lift_x_even_y(str_to_bytes(pubkey))
    assert Pi is not None
    ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))
    # Lấy kết quả là int từ mã bytes
    Xi = point_mul(Pi,ai)
    return Xi

def getpublickey(priKey):
    privkey_int = int_from_hex(priKey)
    publickey = pubkey_point_gen_from_int(privkey_int)

    # Check if the point P has the y-coordinate even; negate the private key otherwise
    privkey_even = privkey_int if has_even_y(publickey) else n - privkey_int

    hex_privkey = hex(privkey_even).replace('0x', '').rjust(64, '0')
    return bytes_from_point(publickey).hex()


# Get bytes from an int
def bytes_from_int(a: int) -> bytes:
    return a.to_bytes(32, byteorder="big")


# Get bytes from a hex
def bytes_from_hex(a: hex) -> bytes:
    return unhexlify(a)


# Get bytes from a point
def bytes_from_point(P: Point) -> bytes:
    return bytes_from_int(x(P))


# Get an int from bytes
def int_from_bytes(b: bytes) -> int:
    return int.from_bytes(b, byteorder="big")


# Get an int from hex
def int_from_hex(a: hex) -> int:
    return int.from_bytes(unhexlify(a), byteorder="big")


# Get x coordinate from a point
def x(P: Point) -> int:
    return P[0]


# Get y coordinate from a point
def y(P: Point) -> int:
    return P[1]


# Point addition
def point_add(P1: Optional[Point], P2: Optional[Point]) -> Optional[Point]:
    if P1 is None:
        return P2
    if P2 is None:
        return P1
    if (x(P1) == x(P2)) and (y(P1) != y(P2)):
        return None
    if P1 == P2:
        lam = (3 * x(P1) * x(P1) * pow(2 * y(P1), p - 2, p)) % p
    else:
        lam = ((y(P2) - y(P1)) * pow(x(P2) - x(P1), p - 2, p)) % p
    x3 = (lam * lam - x(P1) - x(P2)) % p
    return x3, (lam * (x(P1) - x3) - y(P1)) % p


# Point multiplication
def point_mul(P: Optional[Point], d: int) -> Optional[Point]:
    R = None
    for i in range(256):
        if (d >> i) & 1:
            R = point_add(R, P)
        P = point_add(P, P)
    return R


# Note:
# This implementation can be sped up by storing the midstate
# after hashing tag_hash instead of rehashing it all the time
# Get the hash digest of (tag_hashed || tag_hashed || message)
def tagged_hash(tag: str, msg: bytes) -> bytes:
    tag_hash = hashlib.sha256(tag.encode()).digest()
    return hashlib.sha256(tag_hash + tag_hash + msg).digest()


# Check if a point is at infinity
def is_infinity(P: Optional[Point]) -> bool:
    return P is None


# Get xor of bytes
def xor_bytes(b0: bytes, b1: bytes) -> bytes:
    return bytes(a ^ b for (a, b) in zip(b0, b1))


# Get a point from bytes
def lift_x_square_y(b: bytes) -> Optional[Point]:
    x = int_from_bytes(b)
    if x >= p:
        print("None do x>=p đó")
        return None
    y_sq = (pow(x, 3, p) + 7) % p
    y = pow(y_sq, (p + 1) // 4, p)
    if pow(y, 2, p) != y_sq:
        return None
    return x, y


def lift_x_even_y(b: bytes) -> Optional[Point]:
    P = lift_x_square_y(b)
    if P is None:
        return None
    else:
        return x(P), y(P) if y(P) % 2 == 0 else p - y(P)


# Get hash digest with SHA256
def sha256(b: bytes) -> bytes:
    return hashlib.sha256(b).digest()


# Check if an int is square
def is_square(a: int) -> bool:
    return int(pow(a, (p - 1) // 2, p)) == 1


# Check if a point has square y coordinate
def has_square_y(P: Optional[Point]) -> bool:
    infinity = is_infinity(P)
    if infinity:
        return False
    assert P is not None
    return is_square(y(P))


# Check if a point has even y coordinate
def has_even_y(P: Point) -> bool:
    return y(P) % 2 == 0


# Generate public key from an int
def pubkey_gen_from_int(seckey: int) -> bytes:
    P = point_mul(G, seckey)
    assert P is not None
    return bytes_from_point(P)


# Generate public key from a hex
def pubkey_gen_from_hex(seckey: hex) -> bytes:
    seckey = bytes.fromhex(seckey)
    d0 = int_from_bytes(seckey)
    if not (1 <= d0 <= n - 1):
        raise ValueError(
            'The secret key must be an integer in the range 1..n-1.')
    P = point_mul(G, d0)
    assert P is not None
    return bytes_from_point(P)


# Generate public key (as a point) from an int
def pubkey_point_gen_from_int(seckey: int) -> Point:
    P = point_mul(G, seckey)
    assert P is not None
    return P


# Generate auxiliary random of 32 bytes
def get_aux_rand() -> bytes:
    return os.urandom(32)


# Extract R_x int value from signature
def get_int_R_from_sig(sig: bytes) -> int:
    return int_from_bytes(sig[0:32])


# Extract s int value from signature
def get_int_s_from_sig(sig: bytes) -> int:
    return int_from_bytes(sig[32:64])


# Extract R_x bytes from signature
def get_bytes_R_from_sig(sig: bytes) -> int:
    return sig[0:32]


# Extract s bytes from signature
def get_bytes_s_from_sig(sig: bytes) -> int:
    return sig[32:64]





# Generate Schnorr signature
def schnorr_sign(msg: bytes, privateKey: str) -> bytes:
    if len(msg) != 32:
        raise ValueError('The message must be a 32-byte array.')
    d0 = int_from_hex(privateKey)
    if not (1 <= d0 <= n - 1):
        raise ValueError(
            'The secret key must be an integer in the range 1..n-1.')
    P = point_mul(G, d0)
    assert P is not None
    d = d0 if has_even_y(P) else n - d0
    t = xor_bytes(bytes_from_int(d), tagged_hash("BIP0340/aux", get_aux_rand()))
    k0 = int_from_bytes(tagged_hash("BIP0340/nonce", t + bytes_from_point(P) + msg)) % n
    if k0 == 0:
        raise RuntimeError('Failure. This happens only with negligible probability.')
    R = point_mul(G, k0)
    assert R is not None
    k = n - k0 if not has_even_y(R) else k0
    e = int_from_bytes(tagged_hash("BIP0340/challenge", bytes_from_point(R) + bytes_from_point(P) + msg)) % n
    sig = bytes_from_point(R) + bytes_from_int((k + e * d) % n)

    if not schnorr_verify(msg, bytes_from_point(P), sig):
        raise RuntimeError('The created signature does not pass verification.')
    return sig


# Verify Schnorr signature
def schnorr_verify(msg: bytes, pubkey: bytes, sig: bytes) -> bool:
    if len(msg) != 32:
        raise ValueError('The message must be a 32-byte array.')
    if len(pubkey) != 32:
        raise ValueError('The public key must be a 32-byte array.')
    if len(sig) != 64:
        raise ValueError('The signature must be a 64-byte array.')
    P = lift_x_even_y(pubkey)
    r = get_int_R_from_sig(sig)
    s = get_int_s_from_sig(sig)
    if (P is None) or (r >= p) or (s >= n):
        return False
    e = int_from_bytes(tagged_hash("BIP0340/challenge", get_bytes_R_from_sig(sig) + pubkey + msg)) % n
    R = point_add(point_mul(G, s), point_mul(P, n - e))
    if (R is None) or (not has_even_y(R)):
        # print("Please, recompute the sign. R is None or has even y")
        return False
    if x(R) != r:
        # print("There's something wrong")
        return False
    return True

# Generate Schnorr MuSig signature
def schnorr_musig_sign(msg: bytes, users: list) -> bytes:

    #Kiểm tra msg

    if len(msg) != 32:
        raise ValueError('The message must be a 32-byte array.')

    #Tạo khóa chung nè
    # Key aggregation (KeyAgg), L = h(P1 || ... || Pn)
    L = b''

    #Nhét publickey vào đây dùm
    for u in users:
        L += pubkey_gen_from_hex(u["privateKey"])
        print('L: ',L)
    L = sha256(L)

    Rsum = None
    X = None
    for u in users:
        # Get private key di and public key Pi
        di = int_from_hex(u["privateKey"])
        if not (1 <= di <= n - 1):
            raise ValueError('The secret key must be an integer in the range 1..n-1.')
        Pi = pubkey_point_gen_from_int(di)
        assert Pi is not None

        # KeyAggCoef
        # ai = h(L||Pi)
        ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))
        u["ai"] = ai

        # Computation of X~
        # X~ = X1 + ... + Xn, Xi = ai * Pi
        X = point_add(X, point_mul(Pi, ai))

        # Random ki with tagged hash
        t = xor_bytes(bytes_from_int(di), tagged_hash("BIP0340/aux", get_aux_rand()))
        ki = int_from_bytes(tagged_hash("BIP0340/nonce", t + bytes_from_point(Pi) + msg)) % n
        if ki == 0:
            raise RuntimeError('Failure. This happens only with negligible probability.')

        # Ri = ki * G
        Ri = point_mul(G, ki)
        assert Ri is not None

        # Rsum = R1 + ... + Rn
        Rsum = point_add(Rsum, Ri)
        u["ki"] = ki

    # The aggregate public key X~ needs to be y-even
    if not has_even_y(X):
        for i, u in enumerate(users):
            users[i]["ai"] = n - u["ai"]

    # If the aggregated nonce does not have an even Y
    # then negate  individual nonce scalars (and the aggregate nonce)
    if not has_even_y(Rsum):
        for i, u in enumerate(users):
            users[i]["ki"] = n - u["ki"]

    # c = hash( Rsum || X || M )
    c = int_from_bytes(tagged_hash("BIP0340/challenge", (bytes_from_point(Rsum) + bytes_from_point(X) + msg))) % n

    sSum = 0
    for u in users:
        # Get private key di
        di = int_from_hex(u["privateKey"])

        # sSum = s1 + ... + sn,  # si = ki + di * c * ai mod n
        sSum += (di * c * u["ai"] + u["ki"]) % n
    sSum = sSum % n

    signature_bytes = bytes_from_point(Rsum) + bytes_from_int(sSum)

    if not schnorr_verify(msg, bytes_from_point(X), signature_bytes):
        raise RuntimeError('The created signature does not pass verification.')
    return signature_bytes, bytes_from_point(X)



def schnorr_mugsig_creatGroupKey(users : list) -> bytes:
    #Nhớ là khóa công khai nha
    L = b''
    for u in users:
        L += pubkey_gen_from_hex(u["publicKey"])
    L = sha256(L)

    Rsum = None
    X = None
    for u in users:
        # Get private key di and public key Pi
        di = int_from_hex(u["publicKey"])
        if not (1 <= di <= n - 1):
            raise ValueError('The secret key must be an integer in the range 1..n-1.')
        Pi = pubkey_point_gen_from_int(di)
        assert Pi is not None

        # KeyAggCoef
        # ai = h(L||Pi)
        ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))
        u["ai"] = ai

        # Computation of X~
        # X~ = X1 + ... + Xn, Xi = ai * Pi
        X = point_add(X, point_mul(Pi, ai))
        return bytes_from_point(X)


def schnorr_musig_signbyAn(msg: bytes, users: list) -> bytes:

    #Kiểm tra msg

    if len(msg) != 32:
        raise ValueError('The message must be a 32-byte array.')

    #Tạo khóa chung nè
    # Key aggregation (KeyAgg), L = h(P1 || ... || Pn)
    L = b''

    #Nhét publickey vào đây dùm
    for u in users:
        L += pubkey_gen_from_hex(u["privateKey"])

    L = sha256(L)
    print("Tham số L kiểm tra ngay chính trong hàm là: ",L.hex())

    Rsum = None
    X = None
    for u in users:
        # Get private key di and public key Pi
        di = int_from_hex(u["privateKey"])
        if not (1 <= di <= n - 1):
            raise ValueError('The secret key must be an integer in the range 1..n-1.')
        Pi = pubkey_point_gen_from_int(di)
        assert Pi is not None

        # KeyAggCoef
        # ai = h(L||Pi)
        ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))
        u["ai"] = ai

        # Computation of X~
        # X~ = X1 + ... + Xn, Xi = ai * Pi
        X = point_add(X, point_mul(Pi, ai))
        print("Tham số Pi: ",Pi)
        print("Tham số ai: ",ai)
        print("Các thể loại point Xi từ từ", point_mul(Pi, ai))

        # Random ki with tagged hash
        t = xor_bytes(bytes_from_int(n), tagged_hash("BIP0340/aux", get_aux_rand()))
        ki = int_from_bytes(tagged_hash("BIP0340/nonce", t + bytes_from_point(Pi) + msg)) % n
        if ki == 0:
            raise RuntimeError('Failure. This happens only with negligible probability.')
        print("Giá trị ki: ",ki)
        # Ri = ki * G
        Ri = point_mul(G, ki)
        assert Ri is not None

        # Rsum = R1 + ... + Rn
        Rsum = point_add(Rsum, Ri)
        u["ki"] = ki

    # The aggregate public key X~ needs to be y-even
    if not has_even_y(X):
        for i, u in enumerate(users):
            users[i]["ai"] = n - u["ai"]

    # If the aggregated nonce does not have an even Y
    # then negate  individual nonce scalars (and the aggregate nonce)
    if not has_even_y(Rsum):
        for i, u in enumerate(users):
            users[i]["ki"] = n - u["ki"]

    # c = hash( Rsum || X || M )
    c = int_from_bytes(tagged_hash("BIP0340/challenge", (bytes_from_point(Rsum) + bytes_from_point(X) + msg))) % n

    sSum = 0
    for u in users:
        # Get private key di
        di = int_from_hex(u["privateKey"])

        # sSum = s1 + ... + sn,  # si = ki + di * c * ai mod n
        sSum += (di * c * u["ai"] + u["ki"]) % n
    sSum = sSum % n

    signature_bytes = bytes_from_point(Rsum) + bytes_from_int(sSum)

    if not schnorr_verify(msg, bytes_from_point(X), signature_bytes):
        raise RuntimeError('The created signature does not pass verification.')
    return signature_bytes, bytes_from_point(X)


# Generate Schnorr MuSig2 signature
def schnorr_musig2_sign(msg: bytes, users: list) -> bytes:
    if len(msg) != 32:
        raise ValueError('The message must be a 32-byte array.')

    nu = 2

    # Key aggregation (KeyAgg), L = h(P1 || ... || Pn)
    L = b''
    for u in users:
        L += pubkey_gen_from_hex(u["privateKey"])
    L = sha256(L)

    X = None
    for u in users:
        # Get private key di and public key Pi
        di = int_from_hex(u["privateKey"])
        if not (1 <= di <= n - 1):
            raise ValueError('The secret key must be an integer in the range 1..n-1.')
        Pi = pubkey_point_gen_from_int(di)
        assert Pi is not None

        # KeyAggCoef
        # ai = h(L||Pi)
        ai = int_from_bytes(sha256(L + bytes_from_point(Pi)))
        u["ai"] = ai

        # Computation of X~
        # X~ = X1 + ... + Xn, Xi = ai * Pi
        X = point_add(X, point_mul(Pi, ai))

        # First signing round (Sign and SignAgg)
        r_list = []
        R_list = []

        for j in range(nu):
            # Random r with tagged hash
            t = xor_bytes(bytes_from_int(di), tagged_hash("BIP0340/aux", get_aux_rand()))
            r = int_from_bytes(tagged_hash("BIP0340/nonce", t + bytes_from_point(Pi) + msg)) % n
            if r == 0:
                raise RuntimeError('Failure. This happens only with negligible probability.')

            # Ri,j = ri,j * G (i represents the user)
            Rij = point_mul(G, r)
            assert Rij is not None

            r_list.append(r)
            R_list.append(Rij)
        u["r_list"] = r_list
        u["R_list"] = R_list

    # SignAgg
    # for each j in {1 .. nu} aggregator compute Rj as sum of Rij  (where i goes
    # from 1 to n, and n is the number of user, while j is fixed for each round)
    # Rj is a set, where its size is nu
    Rj_list = []
    for j in range(nu):
        Rj_list.append(None)
        for u in users:
            Rj_list[j] = point_add(Rj_list[j], u["R_list"][j])

    # Second signing round (Sign', SignAgg', Sign'')
    # Sign'
    Rbytes = b''
    for Rj in Rj_list:
        Rbytes += bytes_from_point(Rj)

    # b = h(R1 || ... || Rn || X || M)
    b = sha256(Rbytes + bytes_from_point(X) + msg)

    Rsum = None
    for j, Rj in enumerate(Rj_list):
        # Rsum = SUM (Rj * b^(j))  (Rsum is R in the paper)
        Rsum = point_add(Rsum, point_mul(Rj, int_from_bytes(b) ** j))
    assert Rsum is not None

    # The aggregate public key X~ needs to be y-even
    if not has_even_y(X):
        for i, u in enumerate(users):
            users[i]["ai"] = n - u["ai"]

    # If the aggregated nonce does not have an even Y
    # then negate  individual nonce scalars (and the aggregate nonce)
    if not has_even_y(Rsum):
        for i, u in enumerate(users):
            for j, r in enumerate(users[i]["r_list"]):
                users[i]["r_list"][j] = n - users[i]["r_list"][j]

    # c = hash( Rsum || X || M )
    c = int_from_bytes(tagged_hash("BIP0340/challenge", (bytes_from_point(Rsum) + bytes_from_point(X) + msg))) % n

    # SignAgg' step
    sSum = 0
    for u in users:
        # Get private key di
        di = int_from_hex(u["privateKey"])

        rb = 0
        for j in range(nu):
            rb += u["r_list"][j] * int_from_bytes(b) ** j

        # ssum = s1 + ... + sn, si = (c*ai*di + r) % n
        sSum += (di * c * u["ai"] + rb) % n
    sSum = sSum % n

    signature_bytes = bytes_from_point(Rsum) + bytes_from_int(sSum)

    if not schnorr_verify(msg, bytes_from_point(X), signature_bytes):
        raise RuntimeError('The created signature does not pass verification.')
    return signature_bytes, bytes_from_point(X)

def ma_hoa_ECC(msg: bytes, pubkey: bytes):
    P1 = point_mul(G,10)
    P21 = lift_x_even_y(msg)
    print(P21)
    P22 = point_mul(lift_x_even_y(str_to_bytes(pubkey)),2)
    P2 = point_add(P21,P22)
    return P1,P2

def giai_ma_ECC(P1,P2,privkey):
    tmp = int_from_bytes(str_to_bytes(privkey))
    return point_add(P2,point_mul(P1,-tmp ))



def createpubkeysfromprikeys(array):
    # Create json
    users = {
        "users": [],
    }

    # Generate n keys
    for i in range(0, len(array)):
        privkey_input_hex = array[i]
        privkey_int = int_from_hex(privkey_input_hex)

        publickey = pubkey_point_gen_from_int(privkey_int)

        # Kiểm tra nếu privkey là số chẵn thì biến đổi, số lẻ giữ nguyên
        privkey_even = privkey_int if has_even_y(publickey) else n - privkey_int

        hex_privkey = hex(privkey_even).replace('0x', '').rjust(64, '0')
        users["users"].append({
            "privateKey": hex_privkey,
            "publicKey": bytes_from_point(publickey).hex()
        })

    return users
