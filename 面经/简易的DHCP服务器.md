**题目描述**

DHCP服务器的功能是为每一个MAC地址分配唯一的IP地址，请详现一个简易的DHCP服务器，根据请求命令分别
分配或者释族IP地址。


- 分配IP地址范围从 192.168.0.0 到 192.168.0.255 总共256个可用地址。


- 一个MAC地址的分配IP未释放时，再来重复申请，直接返回已分配的IP地址。


- 一个MAC地址的分配IP已释放，再来申请IP，优先分配之前有最近一次分配的IP地址。



- 按升序分配从未使用过的IP地址; 如时从未使用过的IP地址耗尽，控升序分配已释放出来的IP地址，仍然无法分配到的返回`NA`


**输入**

首行为整数n, 表示其后输入的命令行数，范围[1,2000]。

之后每行为一条命令，格式为: `命令=MAC地址`

命令只有两种:`REQUEST` 和`RELEASE`，例如:

REQUEST=AABBCCDDEEF1 表示给MAC地址为AABBCCDDEEF1分配一个IP地址

RELEASE=AABBCCDDEEF1 表示释放MAC地址之前分配的IP地址

注: MAC地址为12个大写英文字母或数字。

**输出**

1.REQUEST命令，输出分配和结果(lP地址或`NA` )。

2.RELEASE命令，不输出任何内容。


**样例**

输入样例1

	2
	REQUEST=AABBCCDDEEF1
	RELEASE=AABBCCDDEEF1

输出样例2

	192.168.0.0

输入样例1

	4
	REQUEST=AABBCCDDEEF1
	RELEASE=AABBCCDDEEF1
	REQUEST=AABBCCDDEEF2
	REQUEST=AABBCCDDEEF1

输出样例2

	192.168.0.0
	192.168.0.1
	192.168.0.0

**提示**

样例1

REQUEST=AABBCCDDEEF1 按升序分配从未使用过的IP地址，输出192.168.0.0

RELEASE=AABBCCDDEEF1 不输出

样例2

REQUEST=AABBCCDDEEF1 按升序分配从未使用过的IP地址，输出192.168.0.0

RELEASE=AABBCCDDEEF1 不输出

REQUEST=AABBCCDDEEF2 按升序分配从未使用过的IP地址，轴出192.168.0.1

REQUEST=AABBCCDDEEF1 优先分配之前最近一次分配的IP地址，输出192.168.0.0

	#include <iostream>
	#include <string>
	#include <unordered_map>
	
	using namespace std;
	
	const string HEAD_IP = "192.168.0.";
	class MiniDhcpServer {
	
	public:
	    // 未释放的 MAC:IP
	    unordered_map<string, int> m_unRelased;
	    // 已经释放的 MAC:IP
	    unordered_map<string, int> m_relased;
	    // 正在使用的IP
	    bool m_usingIP[256] = {false};
	    // 下一个待分配的IP
	    int m_nextIP = 0;
	    // 已经分配的数量
	    int m_capacity = 0;
	    string Request(const string &mac)
	    {
	        auto it = m_unRelased.find(mac);
	        if (it != m_unRelased.end()) {
	            // 未释放IP
	            return HEAD_IP + to_string((*it).second);
	        } else if (m_capacity >= 256){
	            // 没有足够的IP
	            return "NA";
	        }else if ((it = m_relased.find(mac)) != m_relased.end() && !m_usingIP[(*it).second] ) {
	            // 已经释放, 且未被占用
	            int tempIp = (*it).second;
	            m_usingIP[tempIp] = true; // 占用IP
	            m_unRelased[mac] = tempIp; // 添加到未释放
	            m_capacity++;
	            return HEAD_IP + to_string(tempIp);
	        } else {
	            // 需要重新分配
	            int curIP = m_nextIP; // 记录要返回的IP
	            m_usingIP[curIP] = true; // 将该IP记录为已使用
	            m_unRelased[mac] = curIP; // 将IP更新在map中
	            m_relased[mac] = curIP; // 将IP更新在map中
	            m_capacity++;
	            while (m_capacity < 256 && m_usingIP[m_nextIP]) {
	                m_nextIP = (m_nextIP + 1) % 256; // 寻找下一个可用IP
	            }
	            return HEAD_IP + to_string(curIP);
	        }
	    }
	
	    void Release(const string &mac)
	    {
	        auto it = m_unRelased.find(mac);
	        if (it == m_unRelased.end()) {
	            return;
	        }
	        m_capacity--;
	        int tempIp = (*it).second;
	        m_usingIP[tempIp] = false;
	        m_unRelased.erase(mac);
	    }
	};
	
	int main()
	{
	    int line;
	    cin >> line;
	
	    MiniDhcpServer dhcp;
	    for (int loop = 0; loop < line; loop++) {
	        string str;
	        cin >> str;
	        string opration = str.substr(0, str.find_first_of("="));
	        cout << opration << endl;
	        string mac = str.substr(str.find_first_of("=") + 1);
	        cout << mac << endl;
	
	        if (opration == "REQUEST") {
	            cout << dhcp.Request(mac) << endl;
	        } else if (opration == "RELEASE") {
	            dhcp.Release(mac);
	        }
	    }
	   
	    return 0;
	}
