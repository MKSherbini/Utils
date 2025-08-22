do {
  $dirs = gci $tdc -directory -recurse | Where { (gci $_.fullName -Force).count -eq 0 } | select -expandproperty FullName
  $dirs | Foreach-Object { Remove-Item $_ }
} while ($dirs.count -gt 0)