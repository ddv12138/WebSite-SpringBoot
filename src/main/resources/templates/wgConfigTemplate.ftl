<#--@formatter:off-->
[Interface]
PrivateKey = ${anInterface.privateKey}
Address = ${anInterface.address}
<#if anInterface.listenPort??>
ListenPort = ${anInterface.listenPort?c}
</#if>
<#if anInterface.DNS??>
DNS = ${anInterface.DNS}
</#if>
<#if anInterface.postUp??>
PostUp = ${anInterface.postUp}
</#if>
<#if anInterface.postDown??>
PostDown = ${anInterface.postDown}
</#if>

<#list peers as peer>
[Peer]
PublicKey = ${peer.publicKey}
AllowedIPs = ${peer.allowedIPs}
<#if peer.endpoint??>
Endpoint = ${peer.endpoint}
</#if>
PersistentKeepalive = 35
</#list>
<#--@formatter:on-->