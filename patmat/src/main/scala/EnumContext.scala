def empty: Context =
  ???

def cons(name: String, value: Int, rem: Context) =
  ???

def lookup(ctx: Context, name: String): LookupResult =
  ???

enum LookupResult:
  case Ok(v: Int)
  case NotFound

def erase(ctx: Context, name: String): Context =
  ???