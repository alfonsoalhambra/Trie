#Trie#

With this project I am sharing a simple yet memory-efficient implementation of a **trie** or **retrieval tree**. This data structure allows for fast **insertion**, **removal** and **searches** of both **strings** and **prefixes**. It is nicely introduced in [Wikipedia](https://en.wikipedia.org/wiki/Trie).

##The code and how to use it##

In **src/trie/Trie.java** an implementation of a **trie** can be found providing methods to **insert**, **remove** or **search** a word and **check** if a **prefix** is present in the **trie**.

The implementation of the **trie** relies on the implementation of the **nodes** in the **trie**. Every node acts as the **root** of the subtrie comoposed by it and its children nodes' tries.

The **nodes** **aggregate** in their bodies **substrings** of the words in the trie and store **letters** of the words in the **edges** whenever **two words diverge**. This way, the **number of nodes** to **store** the trie as well as the **number of duplicated characters** stored is **reduced** in comparison to more straightforward trie implementations.

For more technical details about the code, please refer to the **[javadoc pages](https://alfonsoalhambra.github.io/Trie)**.

##Lines of improvement##

* The provided implementation is **not thread safe**. It would be a great idea to make in thread safe. A trivial solution would be to achieve **coarse grained** synchronization by locking the trie level calls to each of the four functions. A more challenging way would be to try **fine grained** synchronization or more advanced synchronization models, ideally **non-blocking synchronization**.
* The provided implementation **stores all the trie in RAM**. It is good for **medium sized datasets** but it would be interesting to consider a more **scalable** implementation partially or totally storing the trie in the **directory system**. Another interesting scalability line would be to make it **distributed** letting **multiple machines** maintain the trie in **RAM and/or disk**.
* The provided implementation behaves as a **set of words**. It can be trivially converted into a **map** transforming the *endNode* flag into a **container of information whose key is the string leading to the final node of the word**. In case more than one element could share the same key, a **set or list structure** would be necessary to replace the *endNode* flag.

##Contact##

In case you have any question about the project feel free to **[mail me](mailto:alfonso.alhambra@gmail.com?Subject=GitHub%20Trie)**, Alfonso Alhambra Morón.
