
## String, StringBuffer, StringBuilder
String: 不可变字符串常量，immutable，内部实现-存放字符的数组被声明为final，实现了Serializable, Comparable，CharSequence接口
StringBuffer(JDK1.0)：线程安全的可变字符串序列(synchronized保证)，append/insert, 缺省capacity=16，扩容：当前， 实现了Serializable, ，CharSequence接口
StringBuilder(JDK5.0): 非线程安全的可变字符串序列， other same with StringBuffer


## transient的用途
当对象被序列化时（写入字节序列到目标文件）时，transient阻止实例中那些用此关键字声明的变量持久化；
当对象被反序列化时（从源文件读取字节序列进行重构），这样的实例变量值不会被持久化和恢复。
例如，当反序列化对象——数据流（例如，文件）可能不存在时，原因是你的对象中存在类型为java.io.InputStream的变量，序列化时这些变量引用的输入流无法被打开。


## Volatile关键字作用？除了保证数据可见性，还有其他什么使用方式？
### volatile关键字无法保证操作的原子性
### 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。

### 禁止进行指令重排序， volatile能在一定程度上保证有序性
volatile关键字禁止指令重排序有两层意思：
1）当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
2）在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。

### volatile的原理和实现机制
前面讲述了源于volatile关键字的一些使用，下面我们来探讨一下volatile到底如何保证可见性和禁止指令重排序的。
下面这段话摘自《深入理解Java虚拟机》：
“观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，加入volatile关键字时，会多出一个lock前缀指令”
lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），内存屏障会提供3个功能：
1）它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；
2）它会强制将对缓存的修改操作立即写入主存；
3）如果是写操作，它会导致其他CPU中对应的缓存行无效。

### 正确使用 volatile 的模式
模式 #1：状态标志
也许实现 volatile 变量的规范使用仅仅是使用一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或请求停机。
模式 #2：一次性安全发布（one-time safe publication）
缺乏同步会导致无法实现可见性，这使得确定何时写入对象引用而不是原语值变得更加困难。在缺乏同步的情况下，可能会遇到某个对象引用的更新值（由另一个线程写入）和该对象状态的旧值同时存在。（这就是造成著名的双重检查锁定（double-checked-locking）问题的根源，其中对象引用在没有同步的情况下进行读操作，产生的问题是您可能会看到一个更新的引用，但是仍然会通过该引用看到不完全构造的对象）。
实现安全发布对象的一种技术就是将对象引用定义为 volatile 类型
模式 #3：独立观察（independent observation）
模式 #4：“volatile bean” 模式

## JVM内存模型
JAVA内存区域：[java堆，方法区] - 线程共享， [虚拟机栈， 本地方法区，程序计数器] - 线程私有

### 程序计数器
多线程时，当线程数超过CPU数量或CPU内核数量，线程之间就要根据时间片轮询抢夺CPU时间资源。因此每个线程有要有一个独立的程序计数器，记录下一条要运行的指令。
线程私有的内存区域。如果执行的是JAVA方法，计数器记录正在执行的java字节码地址，如果执行的是native方法，则计数器为空。

### 虚拟机栈（线程栈）
线程私有的，与线程在同一时间创建。管理JAVA方法执行的内存模型。每个方法执行时都会创建一个桢栈来存储方法的的变量表、操作数栈、动态链接方法、返回值、返回地址等信息。
栈的大小决定了方法调用的可达深度（递归多少层次，或嵌套调用多少层其他方法，-Xss参数可以设置虚拟机栈大小）。栈的大小可以是固定的，或者是动态扩展的。
如果请求的栈深度大于最大可用深度，则抛出stackOverflowError；如果栈是可动态扩展的，但没有内存空间支持扩展，则抛出OutofMemoryError。
使用jclasslib工具可以查看class类文件的结构

栈的大小可以受到几个因素影响，一个是jvm参数 -XSS，默认值随着虚拟机版本以及操作系统影响，从Oracle官网上我们可以找到：

In Java SE 6, the default on Sparc is 512k in the 32-bit VM, and 1024k in the 64-bit VM.
On x86 Solaris/Linux it is 320k in the 32-bit VM and 1024k in the 64-bit VM.
我们可以认为64位linux默认是1m的样子。
除了JVM设置，我们还可以在创建Thread的时候手工指定大小：

public Thread(ThreadGroup group, Runnable target, String name , long stackSize)
栈的大小影响到了线程的最大数量，尤其在大流量的server中，我们很多时候的并发数受到的是线程数的限制，这时候需要了解限制在哪里。
第一个限制在操作系统，以ubuntu为例，/proc/sys/kernel/threads-max 和/proc/sys/vm/max_map_count 定义了总的最大线程数（根据资料windows总的来说线程数会更少）和mmap这个system_call的最大数量（也就是从内存方面限制了线程数）
第二个限制自然是在JVM，理论上我们能分配给线程的内存除以单个线程占用的内存就是最大线程数。所以说对Java进程来讲，既然分配给了堆，栈和静态方法区（或叫永久代，perm区），我们可以大致认为



### 本地方法区
和虚拟机栈功能相似，但管理的不是JAVA方法，是本地方法，本地方法是用C实现的。

### JAVA堆
线程共享的，存放所有对象实例和数组。垃圾回收的主要区域。可以分为新生代和老年代(tenured)。
新生代用于存放刚创建的对象以及年轻的对象，如果对象一直没有被回收，生存得足够长，老年对象就会被移入老年代。
新生代又可进一步细分为eden、survivorSpace0(s0,from space)、survivorSpace1(s1,to space)。
刚创建的对象都放入eden,s0和s1都至少经过一次GC并幸存。如果幸存对象经过一定时间仍存在，则进入老年代(tenured)。

首先堆可以划分为新生代和老年代。然后新生代又可以划分为一个Eden区和两个Survivor（幸存）区。
按照规定，新对象会首先分配在Eden中（如果对象过大，比如大数组，将会直接放到老年代）。在GC中，Eden中的对象会被移动到survivor中，
直至对象满足一定的年纪（定义为熬过minor GC的次数），会被移动到老年代。

新生代 ( Young ) 与老年代 ( Old ) 的比例的值为 1:2 ( 该值可以通过参数 –XX:NewRatio 来指定 )
默认的，Eden : from : to = 8 : 1 : 1 ( 可以通过参数 –XX:SurvivorRatio 来设定 )，即： Eden = 8/10 的新生代空间大小，from = to = 1/10 的新生代空间大小。


### 方法区
线程共享的，用于存放被虚拟机加载的类的元数据信息：如常量、静态变量、即时编译器编译后的代码。也成为永久代。如果hotspot虚拟机确定一个类的定义信息不会被使用，也会将其回收。
回收的基本条件至少有：所有该类的实例被回收，而且装载该类的ClassLoader被回收

------
### 垃圾收集
* 垃圾收集的意义

** 垃圾收集的出现解放了C++中手工对内存进行管理的大量繁杂工作，手工malloc,free不仅增加程序复杂度，还增加了bug数量。
** 分代收集。即在新生代和老生代使用不同的收集方式。在垃圾收集上，目标主要有：加大系统吞吐量（减少总垃圾收集的资源消耗）；
减少最大STW（Stop-The-World）时间；减少总STW时间。不同的系统需要不同的达成目标。而分代这一里程碑式的进步首先极大减少了STW，然后可以自由组合来达到预定目标。

* 可达性检测
** 引用计数：一种在jdk1.2之前被使用的垃圾收集算法，我们需要了解其思想。其主要思想就是维护一个counter，当counter为0的时候认为对象没有引用，可以被回收。
缺点是无法处理循环引用。目前iOS开发中的一个常见技术ARC（Automatic Reference Counting）也是采用类似的思路。在当前的JVM中应该是没有被使用的。
** 根搜算法：思想是从gc root根据引用关系来遍历整个堆并作标记，称之为mark，等会在具体收集器中介绍并行标记和单线程标记。之后回收掉未被mark的对象，好处是解决了循环依赖这种『孤岛效应』。这里的gc root主要指：
a.虚拟机栈(栈桢中的本地变量表)中的引用的对象
b.方法区中的类静态属性引用的对象
c.方法区中的常量引用的对象
d.本地方法栈中JNI的引用的对象

* 整理策略

** 复制：主要用在新生代的回收上，通过from区和to区的来回拷贝。需要特定的结构（也就是Young区现在的结构）来支持，
对于新生成的对象来说，频繁的去复制可以最快的找到那些不用的对象并回收掉空间。所以说在JVM里YGC一定承担了最大量的垃圾清除任务。
** 标记清除/标记整理：主要用在老生代回收上，通过根搜的标记然后清除或者整理掉不需要的对象

思考一下复制和标记清除/整理的区别，为什么新生代要用复制？因为对新生代来讲，一次垃圾收集要回收掉绝大部分对象，
我们通过冗余空间的办法来加速整理过程（不冗余空间的整理操作要做swap，而冗余只需要做move）。
同时可以记录下每个对象的『年龄』从而优化『晋升』操作使得中年对象不被错误放到老年代。
而反过来老年代偏稳定，我们哪怕是用清除，也不会产生太多的碎片，并且整理的代价也并不会太大。
