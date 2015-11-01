package trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Optimized implementation of a trie aiming to minimize the amount of nodes by
 * aggregating into one node multiple characters.
 *
 * @author Alfonso Alhambra Moron
 */
public class Trie {

    /**
     * Root node of the trie.
     */
    private final TrieNode root;

    /**
     * Trie constructor
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     *
     * @param word Word to insert in the trie.
     */
    public void insert(String word) {
        root.insert(word);
    }

    /**
     * Removes a word from the trie.
     *
     * @param word Word to be removed from the trie.
     */
    public void remove(String word) {
        root.remove(word);
    }

    /**
     * Returns true if the word is in the trie, false otherwise.
     *
     * @param word Word to search in the trie.
     * @return true if the word is in the trie, false otherwise.
     */
    public boolean search(String word) {
        return root.search(word);
    }

    /**
     * Returns true if there is any word in the trie that starts with the given
     * prefix, false otherwise.
     *
     * @param prefix Prefix to check in the trie.
     * @return true if there is any word in the trie that starts with the given
     * prefix, false otherwise.
     */
    public boolean startsWith(String prefix) {
        return root.startsWith(prefix);
    }
}

/**
 * TrieNode class. Represents a node in the trie and implements all the logic to
 * allow insertions, deletions searches and prefix checks.
 *
 * @author Alfonso
 */
class TrieNode {

    /**
     * Buffer of content in the current node (the letters in content are present
     * if only one word in the trie contains them).
     */
    private char[] content;
    /**
     * Onse son by letter (for a production setup this structure can be further
     * improved using a balanced tree or something similar to optimise memory
     * usage)
     */
    private Map<Character, TrieNode> sons;
    /**
     * Flag to determine wether if the node reach the end of a word in the trie
     * or not.
     */
    private boolean endNode;

    /**
     * TrieNode constructor. The content is initialized by default as an empty
     * char array, the sons is an empty hash map and by default, the constructed
     * node is not and end node.
     */
    public TrieNode() {
        content = new char[0];
        sons = new HashMap<Character, TrieNode>();
        endNode = false;
    }

    /**
     * Inserts a word. This method should only be used in the root node of the
     * trie.
     *
     * @param word Word to insert in the trie.
     */
    public void insert(String word) {
        if (word != null) {
            insert(word.toCharArray(), 0, true);
        }
    }

    /**
     * Inserts a word or a suffix of a word in the trie taking the current node
     * as root.
     *
     * @param word Word to insert.
     * @param idx Index pointing to the first character of the word visible for
     * the current node.
     * @param insertionEndNode True if the word is an final word, false if it is
     * a subset of a word (i.e. when diving an intermediate node into two
     * nodes).
     */
    private void insert(char[] word, int idx, boolean insertionEndNode) {
        if (content.length == 0 && idx < word.length && sons.isEmpty()) {
            if (!endNode) {
                content = (idx == 0 ? word : Arrays.copyOfRange(word, idx, word.length));
                endNode = insertionEndNode;
            } else {
                TrieNode wordInsertionPoint = new TrieNode();
                sons.put(word[idx], wordInsertionPoint);
                wordInsertionPoint.insert(word, idx + 1, insertionEndNode);
            }
        }
        int i = 0;
        while (i < content.length && i + idx < word.length && content[i] == word[i + idx]) {
            i++;
        }
        idx += i;
        if (i < content.length) {
            char contentMidPoint = content[i];
            TrieNode son = new TrieNode();
            son.content = Arrays.copyOfRange(content, i + 1, content.length);
            son.sons = sons;
            son.endNode = endNode;
            sons = new HashMap<Character, TrieNode>();
            content = Arrays.copyOf(content, i);
            sons.put(contentMidPoint, son);
            endNode = (idx == word.length ? insertionEndNode : false);
        }
        if (idx < word.length) {
            char wordMidPoint = word[idx];
            TrieNode wordInsertionPoint = sons.get(wordMidPoint);
            if (wordInsertionPoint == null) {
                wordInsertionPoint = new TrieNode();
                sons.put(wordMidPoint, wordInsertionPoint);
            }
            wordInsertionPoint.insert(word, idx + 1, insertionEndNode);
        }
        if (idx == word.length) {
            endNode = insertionEndNode;
        }
    }
    
    /**
     * Removes a word. This method should only be used in the root node of the
     * trie.
     *
     * @param word Word to remove in the trie.
     */
    public void remove(String word) {
        if (word != null) {
            remove(word.toCharArray(), 0, null, null);
        }
    }

    /**
     * Removes a word or a suffix of a word in the trie taking the current node
     * as root.
     *
     * @param word Word to remove.
     * @param idx Index pointing to the first character of the word visible for
     * @param parent Parent of the node.
     * @param parentKey Position of the current node in the sons map of the parent.
     * the current node.
     */
    public void remove(char[] word, int idx, TrieNode parent, Character parentKey) {
        int i = 0;
        while (i < content.length && i + idx < word.length && content[i] == word[i + idx]) {
            i++;
        }
        idx += i;
        if (i == content.length && idx == word.length) {
            if (endNode) {
                if (sons.isEmpty() && parent != null && parentKey != null) { // It is a leaf node
                    parent.sons.remove(parentKey);
                } else if (sons.size() == 1) { // The deleted word is a suffix of one or more longer words which match in more than one char within themselves but do not match with any other in that set
                    char nextChar = sons.keySet().toArray(new Character[1])[0];
                    TrieNode fusionNode = sons.get(nextChar);
                    char[] fusionedContent = new char[content.length + 1 + fusionNode.content.length];
                    for(int j = 0; j < fusionedContent.length; j++) {
                        fusionedContent[j] = (j < content.length ? content[j] : (j == content.length ? nextChar : fusionNode.content[j - (content.length + 1)]));
                    }
                    sons.remove(nextChar);
                    content = fusionedContent;
                    for(Entry<Character, TrieNode> newSon : fusionNode.sons.entrySet()) {
                        sons.put(newSon.getKey(), newSon.getValue());
                    }
                    endNode = fusionNode.endNode;
                } else { // The deleted word is a suffix of many other words
                    endNode = false;
                }
            }
        } else if (idx < word.length) {
            TrieNode nextNode = sons.get(word[idx]);
            if (nextNode != null) {
                nextNode.remove(word, idx + 1, this, word[idx]);
            }
        }
    }
    
    /**
     * Checks whether if a word is present in the trie taking the current node
     * as root or not.
     *
     * @param word Word to search.
     * @return True if the word is found in the trie, false otherwise.
     */
    public boolean search(String word) {
        if (word == null) {
            return false;
        }
        return !(search(word.toCharArray(), 0) == null);
    }

    /**
     * Searches the end node in which the given word ends. It will return null
     * if the word is not part of the trie.
     *
     * @param word Word to search.
     * @param idx Index pointing to the first character of the word visible for
     * the current node.
     * @return The end node in which the given word ends. It will return null if
     * the word is not part of the trie.
     */
    private TrieNode search(char[] word, int idx) {
        int i = 0;
        while (i < content.length && i + idx < word.length && content[i] == word[i + idx]) {
            i++;
        }
        idx += i;
        if (i < content.length) {
            return null;
        } else if (i == content.length && idx == word.length) {
            return (endNode ? this : null);
        } else if (idx < word.length) {
            TrieNode nextNode = sons.get(word[idx]);
            return (nextNode == null ? null : nextNode.search(word, idx + 1));
        } else {
            return null;
        }
    }

    /**
     * Checks whether if a word starting with the given prefix is part of the
     * trie or not.
     *
     * @param prefix Prefix to be checked.
     * @return True if a word with the given prefix is found, false otherwise.
     */
    public boolean startsWith(String prefix) {
        if (prefix == null) {
            return false;
        }
        return startsWith(prefix.toCharArray(), 0);
    }

    /**
     * Checks whether if a word starting with the given prefix is part of the
     * trie or not.
     *
     * @param prefix Prefix to be checked.
     * @param idx Index pointing to the first character of the prefix visible
     * for the current node.
     * @return True if a word with the given prefix is found, false otherwise.
     */
    private boolean startsWith(char[] prefix, int idx) {
        int i = 0;
        while (i < content.length && i + idx < prefix.length && content[i] == prefix[i + idx]) {
            i++;
        }
        idx += i;
        if (idx == prefix.length) {
            return true;
        } else if (idx < prefix.length && i == content.length) {
            TrieNode nextNode = sons.get(prefix[idx]);
            return (nextNode == null ? false : nextNode.startsWith(prefix, idx + 1));
        } else {
            return false;
        }
    }
}