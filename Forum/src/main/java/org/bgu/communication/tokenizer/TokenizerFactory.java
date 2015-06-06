package org.bgu.communication.tokenizer;

public interface TokenizerFactory<T> {
   MessageTokenizer<T> create();
}
