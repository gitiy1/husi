package main

import (
	"bytes"
	"cmp"
	"net"

	"github.com/sagernet/sing-box/common/geosite"
)

func compareIPNet(a, b *net.IPNet) int {
	if a.IP.Equal(b.IP) {
		return bytes.Compare(a.Mask, b.Mask)
	}
	return bytes.Compare(a.IP, b.IP)
}

func compareGeositeItem(a, b geosite.Item) int {
	if a.Type == b.Type {
		return cmp.Compare(a.Value, b.Value)
	}
	return cmp.Compare(a.Type, b.Type)
}
