# Terra ![](https://circleci.com/gh/transferwise/terra/tree/master.svg?style=shield&circle-token=d21528d74884febe70e52b220c13b58e763a375b)

A minimalistic library for _object hydration_. Useful for data to object reconstruction mechanics.

## Usage

Given a value object like the following

```java
import java.util.regex.Pattern;

class Email {
    private static final Pattern FORMAT = 
        Pattern.compile("^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    private final String value;
    
    ComputerScientist(String aValue) {
        if (!FORMAT.matcher(aValue).matches()) {
            throw new RuntimeException("Email " + aValue + " is incorrect");
        }
        value = aValue;
    }
    
    public String getValue() {
        return value;
    }
}
```

Imagine you'd want to rebuild the object, bypassing the constructor validation.

```java

import static com.transferwise.terra.Terra.hydrate;

class Example {
    public static void main(String[] args) {
        Email e = hydrate(Email.class, "value", "not+valid+email@example.com");
        
        System.out.println(e.getValue());
    }
}
```

#### Why would we disable the constructor?

This is a very common behaviour when you reconstruct objects from data stored in your persistence mechanism. The validation rules usually live in the write model, not in the read model. Business constraints change all the time and you might end up with data that does not follow these rules at a given moment. 